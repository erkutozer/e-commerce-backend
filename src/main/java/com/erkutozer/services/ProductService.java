package com.erkutozer.services;


import com.erkutozer.dto.ProductResponse;
import com.erkutozer.entity.Product;
import com.erkutozer.exception.ResourceNotFoundException;
import com.erkutozer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Böyle bir kayıt bulunamadı"));
        return ProductResponse.from(product);
    }

}
