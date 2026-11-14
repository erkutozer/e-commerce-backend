package com.erkutozer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank @Size(min = 3, max = 80) String fullName,
        @NotBlank @Email @Size(min = 3, max = 60) String email,
        @NotBlank @Size(min = 8, max = 72, message = "Şifre en az 8 en fazla 72 karekter arasında olmalı") String password
){}