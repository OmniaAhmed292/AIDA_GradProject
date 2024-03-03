package com.example.aida.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;


@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
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

    @OneToMany(mappedBy = "customer")
    private Set<Customer> customerCustomers;

    @OneToMany(mappedBy = "vendor")
    private Set<Vendor> vendorVendors;

    @OneToMany(mappedBy = "dummyAdmin")
    private Set<DummyAdmin> dummyAdminDummyAdmins;

    @OneToMany(mappedBy = "user")
    private Set<Card> userCards;

    @ManyToMany
    @JoinTable(
            name = "UserImage",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "imageId")
    )
    private Set<Image> userImageImages;

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

    public Set<Vendor> getVendorVendors() {
        return vendorVendors;
    }

    public void setVendorVendors(final Set<Vendor> vendorVendors) {
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

    public Set<Image> getUserImageImages() {
        return userImageImages;
    }

    public void setUserImageImages(final Set<Image> userImageImages) {
        this.userImageImages = userImageImages;
    }

}
