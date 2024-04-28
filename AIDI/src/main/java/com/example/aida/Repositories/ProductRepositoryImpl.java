package com.example.aida.Repositories;

import com.example.aida.Entities.Product;

import com.example.aida.Enums.SortFeild;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public abstract class ProductRepositoryImpl implements ProductRepository{
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> productSearch(String search, Double minRating, Double minPrice,
                                Double maxPrice, Boolean available, Boolean is_used, SortFeild sortFeild, Boolean discount, Pageable pageable){

        Criteria criteria = new Criteria();

        criteria.orOperator(
                Criteria.where("name").regex(search, "i"),
                Criteria.where("category").regex(search, "i"),
                Criteria.where("tags.tagName").regex(search, "i"));

        criteria.and("isShown").is(true);


        //--------------------
        //Filters
        //--------------------

        // Add filters conditionally
        if (minPrice != null && maxPrice != null) {
            criteria.and("price").gte(minPrice).lte(maxPrice);
        }
        if (available) {
            criteria.and("quantity").gt(0);
        }
        if (is_used) {
            criteria.and("is_used").is(true);
        } else {
            criteria.and("is_used").is(false);
        }
        if (discount) {
            criteria.and("discount.percentage").gt(0);
            criteria.and("discount.endDate").gt(LocalDateTime.now());

        }


        //--------------------
        //Rating aggregation
        //--------------------


        // Create a custom AggregationOperation
        AggregationOperation addFields = new AggregationOperation() {
            @Override
            public Document toDocument(AggregationOperationContext context) {
                return new Document("$addFields",
                        new Document("reviews.rating",
                                new Document("$ifNull", Arrays.asList("$reviews.rating", 0))));
            }
        };

        // Create the group operation to calculate the average rating
        GroupOperation avgRatingGroup = Aggregation.group("productId")
                .avg("reviews.rating").as("avgRating");

        criteria.andOperator(Criteria.where("avgRating").gte(minRating));

        // Create the match operation for the minimum average rating
        MatchOperation matchOp = Aggregation.match(criteria);

        //Sorting
        SortOperation sortOp = switch (sortFeild) {
            case PRICE_ASC -> Aggregation.sort(Sort.by(Sort.Order.asc("price")));
            case RATING_DESC -> Aggregation.sort(Sort.by(Sort.Order.desc("avgRating")));
            default -> Aggregation.sort(Sort.by(Sort.Order.desc("updatedAt")));
        };

        // Create the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                matchOp,
                Aggregation.unwind("reviews"),
                avgRatingGroup,
                sortOp,
                //limit to 60 results and skip the first skip results
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );



        // Execute the aggregation
        AggregationResults<Product> results = mongoTemplate.aggregate(aggregation, "products", Product.class);

        return results.getMappedResults();

    }
};
