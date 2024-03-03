package com.example.aida.Entities;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "shelfs")
public class Shelf {

    @Id
    @Column(nullable = false, updatable = false, name = "shelf_id")
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
    private Integer shelfId;

    @Column(nullable = false, length = 50, name = "shelf_name")
    private String shelfName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "shelf")
    private Set<Product> shelfProducts;

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(final Integer shelfId) {
        this.shelfId = shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(final String shelfName) {
        this.shelfName = shelfName;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(final Vendor vendor) {
        this.vendor = vendor;
    }

    public Set<Product> getShelfProducts() {
        return shelfProducts;
    }

    public void setShelfProducts(final Set<Product> shelfProducts) {
        this.shelfProducts = shelfProducts;
    }

}
