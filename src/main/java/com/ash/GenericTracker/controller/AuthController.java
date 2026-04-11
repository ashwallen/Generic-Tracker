package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.dto.LoginRequest;
import com.ash.GenericTracker.dto.RegisterRequest;
import com.ash.GenericTracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    ResponseEntity<ApiResponse<Void>>registerUser(@RequestBody RegisterRequest request){
        authService.register(request);
        ApiResponse apiResponse =new ApiResponse<Void>().builder()
                .success(true)
                .message("User Registered Successfully")
                .status(200)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/login")
    ResponseEntity<ApiResponse<String>>login(@RequestBody LoginRequest request){
        //Filtering for valid request ?
        String token = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true,"Logged In Successfully",token,200));
    }

}
