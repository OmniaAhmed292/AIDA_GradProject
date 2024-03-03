package com.example.aida.Entities;

import jakarta.persistence.*;


@Entity
@Table(name = "specifications")
public class Specification {

    @Id
    @Column(nullable = false, updatable = false, name = "spec_id")
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
    private Integer specId;

    @Column(nullable = false, length = 50, name = "attribute_name")
    private String attributeName;

    @Column(nullable = false, length = 100, name = "attribute_value")
    private String attributeValue;

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(final Integer specId) {
        this.specId = specId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(final String attributeValue) {
        this.attributeValue = attributeValue;
    }

}
