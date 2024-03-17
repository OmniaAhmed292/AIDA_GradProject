package com.example.aida.Entities;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Settings {

    @Field(name = "allow_Deactivated")
    private Boolean AllowDeactivated;

    @Field(name = "allow_email_subscribed")
    private Boolean AllowEmailSubscribed;

    @Field(name = "allow_Email_Cart_recovery")
    private Boolean AllowEmailCartRecovery;

    @Field(name = "allow_Collect_information")
    private String AllowInformationCollection;

}
