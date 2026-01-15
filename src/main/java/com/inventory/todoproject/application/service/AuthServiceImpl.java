package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.auth.LoginRequest;
import com.inventory.todoproject.application.dto.response.AuthResponse;
import com.inventory.todoproject.application.ports.in.AuthUseCase;
import com.inventory.todoproject.application.ports.out.UserRepositoryPort;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.exception.InvalidPasswordException;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import com.inventory.todoproject.infraestructure.security.jwt.JwtTokenProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl  implements AuthUseCase {

    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryPort userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepositoryPort userRepository, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid username or password");
        }

        String token = jwtTokenProvider.generateToken(user);

        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }


    @Override
    public AuthResponse refreshToken(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidPasswordException("Invalid or expired token");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String newToken = jwtTokenProvider.generateToken(user);

        AuthResponse response = new AuthResponse(
                newToken,
                user.getId(),
                user.getUsername(),
                user.getRole()
        );


        return response;
    }
}
