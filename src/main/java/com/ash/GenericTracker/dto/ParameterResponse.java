package com.ash.GenericTracker.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ParameterResponse {

    private UUID parameterId;
    private String name;
    private String dataType;
    private Integer parameterOrder;
}