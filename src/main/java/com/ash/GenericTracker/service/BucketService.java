package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.BucketDto;
import com.ash.GenericTracker.dto.BucketRequestDto;
import com.ash.GenericTracker.entity.Bucket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface BucketService {
    Bucket createBucket(BucketRequestDto request);
    List<BucketDto> fetchBucket(UUID userId);
}
