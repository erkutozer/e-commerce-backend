package com.erkutozer.services;


import com.erkutozer.dto.AuthResponseDto;
import com.erkutozer.dto.LoginRequestDto;
import com.erkutozer.dto.RegisterRequestDto;
import com.erkutozer.dto.UserResponseDto;
import com.erkutozer.entity.Role;
import com.erkutozer.entity.User;
import com.erkutozer.exception.DuplicateResourceException;
import com.erkutozer.exception.InvalidCredentialsException;
import com.erkutozer.exception.ResourceNotFoundException;
import com.erkutozer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto requestDto){
        String email = normalize(requestDto.email());

        if (userRepository.existsByEmail(email)){
            throw new DuplicateResourceException(email + " adında bir kullanıcı zaten var");
        }

        User user = new User();
        user.setFullName(requestDto.fullName());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(requestDto.password()));
        user.setRole(Role.USER);
        return buildResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto loginDto){
        User user = userRepository.findByEmail(normalize(loginDto.email()))
                .filter(u -> passwordEncoder.matches(loginDto.password(), u.getPasswordHash()))
                .orElseThrow(() -> new InvalidCredentialsException("Kullanıcı bilgileriniz uyuşmuyor - erkutt"));
        return buildResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getCurrentUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Böyle bir kullanıcı bulunamadı"));
        return UserResponseDto.from(user);
    }

    private AuthResponseDto buildResponse(User user){
        return new AuthResponseDto(
                jwtService.generateToken(user),
                "Bearer",
                jwtService.getExpiresInSeconds(),
                UserResponseDto.from(user)
        );
    }

    private String normalize(String email){
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
