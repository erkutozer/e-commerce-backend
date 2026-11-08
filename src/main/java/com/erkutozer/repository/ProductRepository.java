package com.erkutozer.repository;

import com.erkutozer.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Modifying
    @Query("update Product p set p.stok = p.stok - :qty where p.id = :id and p.stok >= :qty")
    int decreaseStok(@Param("id") Long id, @Param("qty") int qty);
}
