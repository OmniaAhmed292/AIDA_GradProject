package com.example.aida.Entities;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.catalina.realm.UserDatabaseRealm.getRoles;


@Entity
@Table(name = "users")
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

    @Column(nullable = false, length = 50)
    private String fname;

    @Column(nullable = false, length = 50)
    private String lname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String userType;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String addressCity;

    @Column(nullable = false, length = 100)
    private String addressStreet;

    @Column(length = 10)
    private String addressApartmentNo;

    @Column(length = 10)
    private String addressBuildingNo;


    @Column(length = 255)
    private String image_file_path;

    @OneToMany(mappedBy = "customer")
    private Set<Customer> customerCustomers;

    @OneToOne(mappedBy = "vendor")
    private Vendor vendorVendors;

    @OneToMany(mappedBy = "dummyAdmin")
    private Set<DummyAdmin> dummyAdminDummyAdmins;

    @OneToMany(mappedBy = "user")
    private Set<Card> userCards;



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(final String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(final String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(final String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(final String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(final String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressApartmentNo() {
        return addressApartmentNo;
    }

    public void setAddressApartmentNo(final String addressApartmentNo) {
        this.addressApartmentNo = addressApartmentNo;
    }

    public String getAddressBuildingNo() {
        return addressBuildingNo;
    }

    public void setAddressBuildingNo(final String addressBuildingNo) {
        this.addressBuildingNo = addressBuildingNo;
    }

    public Set<Customer> getCustomerCustomers() {
        return customerCustomers;
    }

    public void setCustomerCustomers(final Set<Customer> customerCustomers) {
        this.customerCustomers = customerCustomers;
    }

    public Vendor getVendorVendors() {
        return vendorVendors;
    }

    public void setVendorVendors(final Vendor vendorVendors) {
        this.vendorVendors = vendorVendors;
    }

    public Set<DummyAdmin> getDummyAdminDummyAdmins() {
        return dummyAdminDummyAdmins;
    }

    public void setDummyAdminDummyAdmins(final Set<DummyAdmin> dummyAdminDummyAdmins) {
        this.dummyAdminDummyAdmins = dummyAdminDummyAdmins;
    }

    public Set<Card> getUserCards() {
        return userCards;
    }

    public void setUserCards(final Set<Card> userCards) {
        this.userCards = userCards;
    }

    public void setImage_file_path(String image_file_path) {
        this.image_file_path = image_file_path;
    }


    public String getImage_file_path() {
        return image_file_path;
    }

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
