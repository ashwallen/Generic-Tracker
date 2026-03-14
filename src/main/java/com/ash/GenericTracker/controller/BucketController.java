package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.dto.BucketDto;
import com.ash.GenericTracker.dto.BucketRequestDto;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.service.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/api", consumes = "Application/json")
@AllArgsConstructor
public class BucketController {
    private BucketService bucketService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Bucket>>createBucket(@RequestBody BucketRequestDto request){
            Bucket bucket = bucketService.createBucket(request);
            ApiResponse<Bucket> response = ApiResponse.<Bucket>builder()
                    .data(bucket)
                    .status(HttpStatus.CREATED.value())
                    .success(true)
                    .message("Created Bucket Successfully").build();
            return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<BucketDto>>>fetchBuckets(Authentication authentication){
        UUID userId = UUID.fromString(authentication.getName());
        List<BucketDto>buckets = bucketService.fetchBucket(userId);
        ApiResponse<List<BucketDto>>response = ApiResponse.<List<BucketDto>>builder()
                .data(buckets)
                .success(true)
                .status(200)
                .message("Fetched All The Bucket Successfully!!!")
                .build();
        return ResponseEntity.ok(response);
    }
}