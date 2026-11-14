package com.erkutozer.controller;

import com.erkutozer.dto.CreateOrderRequestDto;
import com.erkutozer.dto.OrderResponseDto;
import com.erkutozer.dto.OrderSummaryResponseDto;
import com.erkutozer.dto.PageResponseDto;
import com.erkutozer.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@Valid @RequestBody CreateOrderRequestDto request,
                                        @AuthenticationPrincipal Jwt jwt){
        return orderService.createOrder(request, Long.valueOf(jwt.getSubject()));
    }

    @GetMapping("/my")
    public PageResponseDto<OrderSummaryResponseDto> getMyOrders(@AuthenticationPrincipal Jwt jwt,
                                                                @PageableDefault(size = 10)Pageable pageable){
        return orderService.getMyOrders(Long.valueOf(jwt.getSubject()), pageable);
    }

    @GetMapping("/my/{orderNumber}")
    public OrderResponseDto getMyOrder(@PathVariable(name = "id") String orderNumber,
                                       @AuthenticationPrincipal Jwt jwt){
        return orderService.getMyOrder(Long.valueOf(jwt.getSubject()), orderNumber);
    }

}
