package com.erkutozer.dto;

import com.erkutozer.entity.Category;

public record CategoryResponseDto(
        Long id,
        String name
) {

    public static CategoryResponseDto from(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }

}
