package com.erkutozer.repository;

import com.erkutozer.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {

    Page<Order> findByUserId(Long UserId, Pageable pageable);

    @EntityGraph(attributePaths = "items")
    Optional<Order> findByOrderNumberAndUserId(String orderNumber, Long userId);

}
