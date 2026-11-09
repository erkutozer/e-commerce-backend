package com.erkutozer.services;


import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.dto.ProductResponse;
import com.erkutozer.entity.Category;
import com.erkutozer.entity.Product;
import com.erkutozer.exception.ResourceNotFoundException;
import com.erkutozer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public PageResponseDto<ProductResponse> getAllProducts(String search, Pageable pageable){
        Page<Product> page = (search == null || search.isBlank())
                ? productRepository.findAll(pageable)
                : productRepository.findByTitleContainingIgnoreCase(search.trim(), pageable);
        return PageResponseDto.from(page.map(ProductResponse::from));
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Böyle bir kayıt bulunamadı"));
        return ProductResponse.from(product);
    }

}
