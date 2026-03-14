package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.BucketDto;
import com.ash.GenericTracker.dto.BucketRequestDto;
import com.ash.GenericTracker.dto.ParameterDto;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.Parameter;
import com.ash.GenericTracker.entity.User;
import com.ash.GenericTracker.repository.BucketRepository;
import com.ash.GenericTracker.repository.ParameterRepository;
import com.ash.GenericTracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class BucketServiceImpl implements BucketService{

    private UserRepository userRepository;
    private BucketRepository bucketRepository;
    private ParameterRepository parameterRepository;

    @Override
    @Transactional
    public Bucket createBucket(BucketRequestDto request) {

        User user  = userRepository.findById(request.getUserId())
                .orElseThrow(()->new RuntimeException("User is not a valid user"));

        Bucket bucket = Bucket.builder()
                .bucketName(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();

        bucketRepository.save(bucket);

        List<Parameter> param  = request.getParameters().stream()
                .map(p->Parameter.builder()
                        .bucketId(bucket)
                        .dataType(p.getDataType())
                        .parameterName(p.getName())
                        .parameterOrder(p.getParameterOrder()).build()).toList();

        parameterRepository.saveAll(param);
        return bucket;
    }

    @Override
    public List<BucketDto> fetchBucket( UUID userId) {
        List<Bucket>buckets = bucketRepository.findByUserId(userId);

        return buckets.stream().map(bucket ->{

            List<Parameter>parameter = parameterRepository.findByBucketIdAndIsActiveTrueOrderByParameterOrder(bucket);

            List<ParameterDto>parameterResponse = parameter.stream().map(param -> ParameterDto.builder()
                    .name(param.getParameterName())
                    .parameterOrder(param.getParameterOrder())
                    .dataType(param.getDataType()).build()).toList();

            return BucketDto.builder().
                    id(bucket.getId())
                    .name(bucket.getBucketName())
                    .description(bucket.getDescription())
                    .parameters(parameterResponse).build();

        }).toList();
    }


}
