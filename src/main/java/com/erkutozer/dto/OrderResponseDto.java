package com.erkutozer.dto;

import com.erkutozer.entity.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDto(
        String orderNumber,
        Instant createAt,
        String fullName,
        String email,
        String phone,
        String city,
        String address,
        BigDecimal totalAmount,
        List<Item> items
) {
    public record Item(Long productId, String title, BigDecimal unitPrice, Integer quantity, BigDecimal lineTotal){}

    public static OrderResponseDto from(Order order){
        List<Item> items = order.getItems().stream()
                .map(i -> new Item(
                        i.getProduct().getId(),
                        i.getProductTitle(),
                        i.getUnitPrice(),
                        i.getQuantity(),
                        i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()))
                )).toList();
        return new OrderResponseDto(order.getOrderNumber(), order.getCreateAt(), order.getFullName(), order.getEmail(),
                order.getPhone(), order.getCity(), order.getAddress(), order.getTotalAmount(), items
                );
    }

}
