package com.example.aida.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class Tag {

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
    private Integer tagId;

    @Column(nullable = false, length = 50)
    private String tagName;

    @ManyToMany(mappedBy = "productTagTags")
    private Set<Product> productTagProducts;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(final Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }

    public Set<Product> getProductTagProducts() {
        return productTagProducts;
    }

    public void setProductTagProducts(final Set<Product> productTagProducts) {
        this.productTagProducts = productTagProducts;
    }

}
