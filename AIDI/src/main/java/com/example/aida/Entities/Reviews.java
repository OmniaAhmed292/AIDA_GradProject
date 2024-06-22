package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Reviews {
    @Id
    private String id;
    @Field("body")
    private String body;
    @Field("rate")
    private Integer rate;

    @Field(name = "customer_id", targetType = FieldType.OBJECT_ID)
    private String customerId;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}