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
import java.util.ArrayList;
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
                Aggregation.project().andExpression("_id").as("month")
                        .andExpression("revenue").as("revenue")
                        .andExpression("ordersCount").as("ordersCount"),
                        //.andExclude("_id"),

                //sort by month
                sort(Sort.by(Sort.Order.asc("month")))
        );


        AggregationResults<MonthSales> results = mongoTemplate.aggregate(aggregation, "orders", MonthSales.class);
        return results.getMappedResults();
    }

    //get the age group of customers who make orders to a vendor
    public CustomerInsightsResponse getCustomerInsights(String vendorId){
        //match vendorId
        Criteria matchCriteria = Criteria.where("order_items.vendor_id").is(new ObjectId(vendorId));

        //unwind order_items
        Aggregation aggregation = Aggregation.newAggregation(
                //match vendorId
                Aggregation.match(matchCriteria),
                //join with customer collection
                Aggregation.lookup("customers", "customer_id", "_id", "customers"),
                //add field age extracted from customer.birthdate (now year subtracted from customer birth year)
                Aggregation.unwind("customers"),
                Aggregation.addFields().addField("age")
                        .withValueOf(ArithmeticOperators.Subtract
                                .valueOf(DateOperators.dateOf("created_at").year())
                                .subtract(DateOperators.dateOf("customers.birthdate").year())).build(),
                //get minimum age
                Aggregation.group().min("age").as("minAgeGroup")
//                        //get maximum age
                        .max("age").as("maxAgeGroup")
                        .sum(ConditionalOperators.when(Criteria.where("customers.gender")
                                .is("Female")).then(1).otherwise(0)).as("femaleCount")
                        .sum(ConditionalOperators.when(Criteria.where("customers.gender")
                                .is("Male")).then(1).otherwise(0)).as("maleCount")


        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "orders", Document.class);
        CustomerInsightsResponse customerInsightsResponse = new CustomerInsightsResponse();
        Document document = results.getUniqueMappedResult();
        if (document != null) {
            customerInsightsResponse.setMinAgeGroup(document.getInteger("minAgeGroup"));
            customerInsightsResponse.setMaxAgeGroup(document.getInteger("maxAgeGroup"));
            customerInsightsResponse.setFemaleCustomersCount(document.getInteger("femaleCount"));
            customerInsightsResponse.setMaleCustomersCount(document.getInteger("maleCount"));
        }
        return customerInsightsResponse;
    }

    public Double getRepeatCustomerRate(String vendor_id){
        //match vendorId
        MatchOperation matchStage = Aggregation.match(Criteria.where("order_items.vendor_id").is(new ObjectId(vendor_id)));
        GroupOperation groupStage1 = Aggregation.group("customer_id").count().as("ordersCount");
        GroupOperation groupStage2 = Aggregation.group().count().as("totalCustomers")
                .sum(ConditionalOperators.when(Criteria.where("ordersCount").gt(1)).then(1).otherwise(0)).as("repeatCustomers");
        ProjectionOperation projectStage = Aggregation.project()
                .andExclude("_id")
                .and(ArithmeticOperators.valueOf("repeatCustomers").divideBy("totalCustomers")).as("repeatCustomerRate");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupStage1, groupStage2, projectStage);

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "orders", Document.class);
        Document document = results.getUniqueMappedResult();
        if (document != null) {
            return document.getDouble("repeatCustomerRate");
        }
        return 0.0;

    }
}
