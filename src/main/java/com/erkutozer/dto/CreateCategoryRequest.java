package com.erkutozer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank @Size(max = 50) String name
) {
}
