package com.example.aida.Entities;

import com.example.aida.Enums.SubscriptionStatus;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Subscription {

    @Field(name = "product_id", targetType = FieldType.OBJECT_ID)
    @NonNull
    private String product_id;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "status")
    private SubscriptionStatus status;
}
