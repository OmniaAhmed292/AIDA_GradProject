package com.example.aida.Repositories;



import com.example.aida.Controllers.Statistics.CustomerInsightsResponse;
import com.example.aida.Controllers.Statistics.MonthSales;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class OrderRepositoryImpl {

    @Autowired
    private  MongoTemplate mongoTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    public List<MonthSales> getMonthlyRevenues(int year, String vendorId){

        // Match year
        LocalDateTime startYear= LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endYear= LocalDateTime.of(year+1, 1, 1, 0, 0);
        Criteria matchCriteria =  new Criteria("created_at").gte(startYear).lt(endYear);
        matchCriteria.and("order_items.vendor_id").is(new ObjectId(vendorId));

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.match(matchCriteria), //match year
                Aggregation.project().andExclude("order_items._id"), //hide order_items._id
                Aggregation.unwind("order_items"),  //unwind order_items

                //add feild inside OrderItem called month
                Aggregation.addFields()
                        .addField("month")
                            .withValueOf(DateOperators.dateOf("created_at").month())
                                .build(),


                //add (product_price - Discount_price)
                Aggregation.addFields().addField("revenue")
                        .withValueOf(ArithmeticOperators.Subtract
                                .valueOf("order_items.Product_price").subtract("order_items.Discount_price")).build(),

                //group by month
                Aggregation.group(Fields.fields().and("month"))
                        .sum("revenue").as("revenue"),
                //change _id to month
                Aggregation.project().and("_id").as("month")
                        .and("revenue").as("revenue"),
                //get the number of order items per month
               Aggregation.group("month").count().as("ordersCount").sum("revenue").as("revenue"),

                //project change _id to month
                Aggregation.project().and("_id").as("month")
                        .and("revenue").as("revenue")
                        .and("ordersCount").as("ordersCount"),
                        //.andExclude("_id"),

                //sort by month
                sort(Sort.by(Sort.Order.asc("month")))
        );


        AggregationResults<MonthSales> results = mongoTemplate.aggregate(aggregation, "orders", MonthSales.class);
        List<MonthSales> monthSales = results.getMappedResults();

        return monthSales ;
    }

    //get the age group of customers who make orders to a vendor
    public CustomerInsightsResponse getCustomerInsights(String vendorId){
        //match vendorId
        Criteria matchCriteria = Criteria.where("order_items.vendor_id").is(vendorId);

        //unwind order_items
        Aggregation aggregation = Aggregation.newAggregation(
                //match vendorId
                Aggregation.match(matchCriteria),
                //join with customer collection
                Aggregation.lookup("customers", "customer_id", "_id", "customers"),
                //add field age extracted from customer.birthdate (now year subtracted from customer birth year)
                Aggregation.addFields().addField("order_items.age")
                        .withValueOf(ArithmeticOperators.Subtract
                                .valueOf(DateOperators.dateOf("created_at").year()).subtract(DateOperators.dateOf("customers.birthdate").year())).build(),
                //get minimum age
                Aggregation.group().min("order_items.age").as("minAgeGroup"),
                //get maximum age
                Aggregation.group().max("order_items.age").as("maxAgeGroup"),
                //get number of female customers
                Aggregation.group("customer_id").count().as("customersCount"),
                Aggregation.match(Criteria.where("customers.gender").is("Female")),
                Aggregation.group("customer_id").count().as("femaleCount"),
                //get number of male customers as customers count - femaleCount
                Aggregation.addFields().addField("maleCount").withValueOf(ArithmeticOperators.Subtract
                        .valueOf("customersCount").subtract("femaleCount")).build()


        );

        AggregationResults<CustomerInsightsResponse> results = mongoTemplate.aggregate(aggregation, "order", CustomerInsightsResponse.class);
        return results.getUniqueMappedResult();
    }

    public Double getRepeatCustomerRate(String vendor_id){
        //match vendorId
        Criteria matchCriteria = Criteria.where("order_items.vendor_id").is(vendor_id);


        Aggregation aggregation = Aggregation.newAggregation(
                //match vendorId
                Aggregation.match(matchCriteria),
                //group by customer_id and count the number of orders
                Aggregation.group("customer_id").count().as("ordersCount"),
                //get the total number of customers
                Aggregation.group().sum("customersCount").as("totalCustomers"),
                //get the number of customers who made more than one order
                Aggregation.match(Criteria.where("customersCount").gt(1)),
                Aggregation.group().count().as("repeatCustomers"),
                //get the repeat customer rate
                Aggregation.addFields().addField("repeatCustomerRate").withValueOf(ArithmeticOperators.Divide
                        .valueOf("repeatCustomers").divideBy("totalCustomers")).build()
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "order", Document.class);
        return Objects.requireNonNull(results.getUniqueMappedResult()).getDouble("repeatCustomerRate");

    }
}
