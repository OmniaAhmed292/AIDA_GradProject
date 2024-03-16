package com.example.aida.Entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.catalina.realm.UserDatabaseRealm.getRoles;


@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "User_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @Column(nullable = false, updatable = false, name = "user_id")
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer userId;

    @Column(precision = 12, scale = 2,name = "balance")
    private BigDecimal balance;


    @Column(nullable = false, length = 50, name="Fname")
    private String fname;

    @Column(nullable = false, length = 50, name = "Lname")
    private String lname;

    @Column(nullable = false, length = 100, name = "email")
    private String email;

    @Column(nullable = false, name = "hashed_password")
    private String hashedPassword;

    @Column(nullable = false, name = "user_type")
    private String userType;

    @Column(nullable = false, length = 15, name = "phone_number")
    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(length = 255, name = "image_file_path")
    private String imageFilePath;

    @Column(length = 255, name = "image_file_name")
    private String imageFileName;

   /* @OneToOne(mappedBy = "customer")
    private Customer cutomer;

    @OneToOne(mappedBy = "vendor")
    private Vendor vendor; */

    @OneToMany(mappedBy = "user")
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
