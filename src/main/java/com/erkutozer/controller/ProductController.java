package com.erkutozer.controller;

import com.erkutozer.dto.ProductResponse;
import com.erkutozer.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-rest/")
public class ProductController {

    private final ProductService productService;

    @GetMapping("getAll")
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

}
