package com.erkutozer.dto;


import com.erkutozer.entity.User;

public record UserResponseDto(
        Long id,
        String fullName,
        String email,
        String role
) {
    public static UserResponseDto from(User user){
        return new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name());
    }
}
