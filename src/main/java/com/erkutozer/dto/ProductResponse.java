package com.erkutozer.dto;

import com.erkutozer.entity.Category;
import com.erkutozer.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        BigDecimal price,
        String title,
        String thumbnail,
        Integer stok,
        String description,
        Long categoryId,
        String categoryName
) {

    public static ProductResponse from(Product product){
        Category category = product.getCategory();
        return new ProductResponse(
                product.getId(),
                product.getPrice(),
                product.getTitle(),
                product.getThumbnail(),
                product.getStok(),
                product.getDescription(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null
        );
    }


}