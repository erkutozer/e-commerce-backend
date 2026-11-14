package com.erkutozer.controller;


import com.erkutozer.dto.CategoryResponseDto;
import com.erkutozer.dto.CreateCategoryRequest;
import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.dto.ProductResponse;
import com.erkutozer.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public List<CategoryResponseDto> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable(name = "id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(request);
    }

}
