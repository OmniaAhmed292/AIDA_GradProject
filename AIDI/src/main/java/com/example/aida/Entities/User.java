package com.example.aida.Entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "users")
@TypeAlias("user_type")
public class User implements UserDetails, Principal {

    @Id
    private String id;


    @Field("Fname")
    private String fname;

    @Field("Lname")
    private String lname;

    @Field("email")
    @Indexed(unique = true)
    private String email;

    @Field("Hashed_Password")
    private String password;

    @Field("User_type")
    private String userType;

    @Field("is_enabled")
    private Boolean isEnabled = true;

    @Field("is_account_locked")
    private Boolean isAccountLocked;
    @Embedded
    @Field("confirmation_tokens")
    private ConfirmationToken confirmationToken;



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

    @Override
    public String getName() {
        return email;
    }
}
