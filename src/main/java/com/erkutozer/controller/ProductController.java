package com.erkutozer.controller;

import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.dto.ProductResponse;
import com.erkutozer.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAll")
    public PageResponseDto<ProductResponse> getAllProducts(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 12, sort = "id") Pageable pageable
            ){
        return productService.getAllProducts(search, pageable);
    }

    @GetMapping("/get/{id}")
    public ProductResponse getProductById(@PathVariable(value = "id") Long id){
        return productService.getProductById(id);
    }

}
