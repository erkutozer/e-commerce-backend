package com.erkutozer.services;

import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.dto.ProductResponse;
import com.erkutozer.entity.Product;
import com.erkutozer.exception.ResourceNotFoundException;
import com.erkutozer.repository.CategoryRepository;
import com.erkutozer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public PageResponseDto<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Kategori bulunamadı: " + categoryId);
        }

        Page<Product> page = productRepository.findByCategoryId(categoryId, pageable);
        return PageResponseDto.from(page.map(ProductResponse::from));
    }

}
