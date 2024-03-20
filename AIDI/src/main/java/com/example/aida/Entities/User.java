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
import org.springframework.security.core.userdetails.UserDetails;


import java.lang.annotation.Documented;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
public class User implements UserDetails {
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
    private String password;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  List.of(new SimpleGrantedAuthority("ROLE_" + userType));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
