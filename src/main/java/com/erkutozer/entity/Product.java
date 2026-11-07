package com.erkutozer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String thumbnail;

    @Column(nullable = false)
    private Integer stok = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

}
