package com.ash.GenericTracker.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private Boolean success;
    private String message;
    private int status;
}
