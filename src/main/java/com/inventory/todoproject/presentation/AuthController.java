package com.inventory.todoproject.presentation;

import com.inventory.todoproject.application.dto.request.auth.LoginRequest;
import com.inventory.todoproject.application.dto.response.AuthResponse;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.exception.SearchCriteria;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import com.inventory.todoproject.domain.repositories.UserRepository;
import com.inventory.todoproject.infraestructure.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }



    //ToDO Tomorrow refactor this class into a Auth service
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        // 2. Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generar el token JWT
        String jwt = jwtTokenProvider.generateToken(authentication);

        // 4. Obtener detalles del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 5. Extraer roles
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // 6. Buscar el usuario en la BD para obtener su ID y email
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        new SearchCriteria("username", userDetails.getUsername())));

        // 7. Construir y retornar la respuesta
        AuthResponse response = new AuthResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );

        return ResponseEntity.ok(response);
    }
}