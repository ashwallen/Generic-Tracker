package com.ash.GenericTracker.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private int status;
    private T data;
}
