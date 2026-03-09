package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.dto.BucketRequest;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.service.BucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/bucket", consumes = "Application/json")

public class BucketController {

    private BucketService bucketService;

    public BucketController(BucketService bucketService){
        bucketService = this.bucketService;
    }

    @PostMapping("/create")
    ResponseEntity<ApiResponse>createBucket(Bucket bucket){
        ApiResponse response =  bucketService.createBucket(bucket);
        return ResponseEntity.ok(response);
    }
}