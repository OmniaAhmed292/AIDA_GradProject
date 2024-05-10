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
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Repository
public class ProductRepositoryImpl {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Product> productSearch(String search, Double minRating, Double minPrice,
                                Double maxPrice, Boolean available, Boolean is_used, SortFeild sortFeild, Boolean discount, Pageable pageable){
        //--------------------
        //Match name, category, and tags
        //--------------------

        Criteria criteria = new Criteria();

        criteria.orOperator(
                Criteria.where("productName").regex(search, "i"),
                Criteria.where("categoryName").regex(search, "i"),
                Criteria.where("tags.tagName").regex(search, "i"));

        criteria.and("isShown").is(true);

        //--------------------
        //Filters
        //--------------------

        // Add filters conditionally
        if (minPrice != null && maxPrice != null) {
            criteria.and("price").gte(minPrice).lte(maxPrice);
        }
        if (available != null && available) {
            criteria.and("quantity").gt(0);
        }
        if (is_used != null) {
            if (is_used) {
                criteria.and("isUsed").is(true);
            } else {
                criteria.and("isUsed").is(false);
            }
        }
        if (discount != null && discount) {
            criteria.and("discount.percentage").gt(0);
            criteria.and("discount.endDate").gt(LocalDateTime.now());

        }

        SortOperation sortOp = switch (sortFeild) {
            case PRICE_ASC -> Aggregation.sort(Sort.by(Sort.Order.asc("price")));
            case RATING_DESC -> Aggregation.sort(Sort.by(Sort.Order.desc("avgRating")));
            default -> Aggregation.sort(Sort.by(Sort.Order.desc("updated_at")));
        };

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.addFields().addFieldWithValue("avgRating",
                        new Document("$cond", Arrays.asList(
                                new Document("$ne", Arrays.asList("$reviews", null)),
                                new Document("$avg", "$reviews.rate"),
                                0
                        ))).build(),
                Aggregation.match(Criteria.where("avgRating").gte(minRating)),
                sortOp,
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<Product> results = mongoTemplate.aggregate(aggregation, "products", Product.class);

        return results.getMappedResults();

    }
};
