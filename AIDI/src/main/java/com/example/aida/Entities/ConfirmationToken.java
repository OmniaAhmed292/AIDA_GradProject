package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ConfirmationToken {
    @Id
    @Field(name = "token_id", targetType = FieldType.OBJECT_ID)
    private Long id;
    @Field("token")
    private String token;
    @Field("creation_date")
    private LocalDateTime createdDate;
    @Field("expiration_date")
    private LocalDateTime expiredDate;
    @Field("confirmed_date")
    private LocalDateTime confirmedDate;
    public ConfirmationToken(String token,
                             LocalDateTime createdDate,
                             LocalDateTime expiredDate) {
        this.token = token;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;


    }
}
