package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.User;
import com.ash.GenericTracker.exception.CustomExceptionHandler;
import com.ash.GenericTracker.repository.BucketRepository;
import com.ash.GenericTracker.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class BucketService {
    private BucketRepository bucketRepository;
    private UserRepository userRepository;
    public ApiResponse createBucket(Bucket bucket) {
        try{
            validBucket(bucket);
        }
        catch (Exception e){
            throw new CustomExceptionHandler(e.getMessage() ,e);
        }
        bucketRepository.save(bucket);
        return new ApiResponse(true,"Inserted Succesfully",1);
    }

    private void validBucket(Bucket bucket){
        UUID id = Optional.ofNullable(bucket).map(Bucket::getUser).map(User::getId).orElse(null);
        if(id==null) throw new CustomExceptionHandler("Invalid User !!");
        else{
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()){
                throw new CustomExceptionHandler("User does not exist. Please Sign UP!!");
            }
        }
        if(bucket.getBucketName()!= null && bucket.getDescription()!=null){
            throw new CustomExceptionHandler("Please enter all the detail for the new Bucket");
        }
    }
}
