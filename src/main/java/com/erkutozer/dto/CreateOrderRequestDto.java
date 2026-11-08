package com.erkutozer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequestDto(
        @NotBlank @Size(min = 3, max = 100) String fullName,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^05\\d{9}$", message = "05 ile başlayan 11 haneli numara girin") String phone,
        @NotBlank String city,
        @NotBlank @Size(min = 3, max = 50, message = "Adres en az 3 karakter içermeli") String address,
        @NotBlank @Valid List<OrderItemRequestDto> items
        ) {
}
