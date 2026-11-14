package com.erkutozer.controller;


import com.erkutozer.dto.AuthResponseDto;
import com.erkutozer.dto.LoginRequestDto;
import com.erkutozer.dto.RegisterRequestDto;
import com.erkutozer.dto.UserResponseDto;
import com.erkutozer.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto requestDto){
        return authService.register(requestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto requestDto){
        return authService.login(requestDto);
    }

    @GetMapping("/me")
    public UserResponseDto me(@AuthenticationPrincipal Jwt jwt){
        return authService.getCurrentUser(Long.valueOf(jwt.getSubject()));
    }


}
