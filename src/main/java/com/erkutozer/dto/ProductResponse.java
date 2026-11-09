package com.erkutozer.dto;

import com.erkutozer.entity.Category;
import com.erkutozer.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        BigDecimal price,
        String title,
        String thumbnail,
        Integer stok
) {

    public static ProductResponse from(Product product){
        Category category = product.getCategory();
        return new ProductResponse(
                product.getId(),
                product.getPrice(),
                product.getTitle(),
                product.getThumbnail(),
                product.getStok()
        );
    }


}