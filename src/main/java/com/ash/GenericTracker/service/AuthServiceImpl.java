package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.AuthResponse;
import com.ash.GenericTracker.dto.LoginRequest;
import com.ash.GenericTracker.dto.RegisterRequest;
import com.ash.GenericTracker.entity.User;
import com.ash.GenericTracker.repository.UserRepository;
import com.ash.GenericTracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void register(RegisterRequest request) {
        String name = request.getName();
        Optional<User> existing = userRepository.findByName(name);
        if(existing.isPresent()){
            throw new RuntimeException("UserName is Taken");
        }
        User user = User.builder()
                        .name(name)
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();
        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {
       User user = userRepository.findByName(request.getName()).orElseThrow(()->new RuntimeException("User Does Not Exist"));
       if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
           throw new RuntimeException("Invalid Credentials");
       return jwtUtil.generateToken(user.getId());
    }
}
