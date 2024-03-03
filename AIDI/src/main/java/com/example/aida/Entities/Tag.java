package com.example.aida.Entities;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @Column(nullable = false, updatable = false, name = "tag_id")
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

    @Column(nullable = false, length = 50, name = "tag_name")
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
