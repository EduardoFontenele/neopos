package com.neopos.adapter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class ProductEntity {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    private String description;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    public ProductEntity(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
