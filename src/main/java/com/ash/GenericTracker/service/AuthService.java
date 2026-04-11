package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.AuthResponse;
import com.ash.GenericTracker.dto.LoginRequest;
import com.ash.GenericTracker.dto.RegisterRequest;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void register(RegisterRequest request);
    String login(LoginRequest request);
}
