package com.ash.GenericTracker.exception;

import java.util.concurrent.ExecutionException;

public class CustomExceptionHandler extends RuntimeException {

    public CustomExceptionHandler(String message,Exception e){
        super(message,e);
    }

    public CustomExceptionHandler(String message){
        super(message);
    }

}
