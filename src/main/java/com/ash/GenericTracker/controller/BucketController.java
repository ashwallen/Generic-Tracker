package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.Parameter;
import com.ash.GenericTracker.service.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api", consumes = "Application/json")
@AllArgsConstructor
public class BucketController {

    private BucketService bucketService;

    @PostMapping("/create/bucket")
    ResponseEntity<ApiResponse>createBucket(Bucket bucket){
        ApiResponse response =  bucketService.createBucket(bucket);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/parameter")
    ResponseEntity<ApiResponse>createParam(Parameter parameter){
        ApiResponse response = bucketService.createParam(parameter);
        return ResponseEntity.ok(response);
    }
}