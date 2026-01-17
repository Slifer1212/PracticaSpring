package com.inventory.todoproject.infrastructure.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class JwtAuthenticationDetails extends WebAuthenticationDetails {

    private final Long userId;

    public JwtAuthenticationDetails(HttpServletRequest request, Long userId) {
        super(request);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
