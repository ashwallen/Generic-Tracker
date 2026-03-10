package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.EntryValue;
import com.ash.GenericTracker.entity.Parameter;
import com.ash.GenericTracker.entity.User;
import com.ash.GenericTracker.exception.CustomExceptionHandler;
import com.ash.GenericTracker.repository.BucketRepository;
import com.ash.GenericTracker.repository.ParameterRepository;
import com.ash.GenericTracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
public class BucketService {
    private BucketRepository bucketRepository;
    private UserRepository userRepository;
    private ParameterRepository parameterRepository;
    public ApiResponse createBucket(Bucket bucket) {
        try{
            validBucket(bucket);
        }
        catch (Exception e){
            throw new CustomExceptionHandler(e.getMessage() ,e);
        }
        //Need to save using query constant. For that need jdbc in the repo .
        bucketRepository.save(bucket);
        //Need to plan how we are sending the response. Is this ok?
        return new ApiResponse(true,"Inserted Bucket Successfully",1);
    }

    public ApiResponse createParam(Parameter parameter){
        String type = parameter.getDataType();
        if(!type.equals("TIME") && !type.equals("DATE") && !type.equals("TEXT"))
            throw new CustomExceptionHandler("Please enter the correct data type for parameter : "+parameter.getParamName());
        try {
            parameterRepository.save(parameter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(true,"Inserted Paramter Successfully",1);
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
