package com.erkutozer.dto;

import com.erkutozer.entity.Order;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderSummaryResponseDto(
        String orderNumber,
        Instant createAt,
        BigDecimal totalAmount
) {

    public static OrderSummaryResponseDto from(Order order){
        return new OrderSummaryResponseDto(order.getOrderNumber(), order.getCreateAt(), order.getTotalAmount());
    }
}
