package com.erkutozer.services;

import com.erkutozer.dto.CategoryResponseDto;
import com.erkutozer.dto.CreateCategoryRequest;
import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.dto.ProductResponse;
import com.erkutozer.entity.Category;
import com.erkutozer.entity.Product;
import com.erkutozer.exception.DuplicateResourceException;
import com.erkutozer.exception.ResourceNotFoundException;
import com.erkutozer.repository.CategoryRepository;
import com.erkutozer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<CategoryResponseDto> getAllCategories(){
        return categoryRepository.findAll(Sort.by("name"))
                .stream()
                .map(CategoryResponseDto::from)
                .toList();
    }

    public PageResponseDto<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Kategori bulunamadı: " + categoryId);
        }

        Page<Product> page = productRepository.findByCategoryId(categoryId, pageable);
        return PageResponseDto.from(page.map(ProductResponse::from));
    }

    public CategoryResponseDto getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı: " + id));
        return CategoryResponseDto.from(category);
    }

    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequest request){
        String name = request.name().trim();

        if (categoryRepository.existsByNameIgnoreCase(name)){
            throw new DuplicateResourceException(name + " adında kategori zaten var!");
        }

        Category category = new Category();
        category.setName(name);
        return CategoryResponseDto.from(categoryRepository.save(category));
    }


}
