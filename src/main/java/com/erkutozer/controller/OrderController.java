package com.erkutozer.controller;

import com.erkutozer.dto.CreateOrderRequestDto;
import com.erkutozer.dto.OrderResponseDto;
import com.erkutozer.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@Valid @RequestBody CreateOrderRequestDto request){
        return orderService.createOrder(request);
    }

}
