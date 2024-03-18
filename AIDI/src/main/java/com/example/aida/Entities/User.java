package com.example.aida.Entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.annotation.Documented;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.catalina.realm.UserDatabaseRealm.getRoles;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "users")
@TypeAlias("user_type")
public class User {
    @Id
    @Field(name = "user_id", targetType = FieldType.OBJECT_ID)
    private String userId;

    @Field("balance")
    private BigDecimal balance;

   @Field("fname")
    private String fname;

    @Field("lname")
    private String lname;

    @Field("email")
    private String email;

    @Field("hashed_password")
    private String hashedPassword;

    @Field("user_type")
    private String userType;

    @Field("phone_number")
    private String phoneNumber;

    @Embedded
    @Field("address")
    private Address address;

    @Embedded
    @Field("image")
    private UserImage userImage;

    @Embedded
    @Field("cards")
    private Set<Card> userCards;

    public Collection<String> getRoles() {

        return Arrays.asList("ADMIN", "Customer","Vendor");
    }
    public Collection<GrantedAuthority> getAuthorities() {
        // Convert roles to GrantedAuthority instances
        return getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
