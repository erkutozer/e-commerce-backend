package com.erkutozer.controller;


import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.dto.ProductResponse;
import com.erkutozer.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}/products")
    public PageResponseDto<ProductResponse> getProductsByCategory(@PathVariable Long id,
                                                                  @PageableDefault(size = 12, sort = "id")Pageable pageable){
        return categoryService.getProductsByCategory(id, pageable);
    }

}
