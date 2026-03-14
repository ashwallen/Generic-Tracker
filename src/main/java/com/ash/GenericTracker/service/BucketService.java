package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.BucketDto;
import com.ash.GenericTracker.dto.BucketRequestDto;
import com.ash.GenericTracker.entity.Bucket;

import java.util.List;
import java.util.UUID;

public interface BucketService {
    Bucket createBucket(BucketRequestDto request);
    List<BucketDto> fetchBucket(UUID userId);
}
