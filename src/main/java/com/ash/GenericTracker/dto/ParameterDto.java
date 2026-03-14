package com.ash.GenericTracker.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {

    private String name;
    private String dataType;
    private Integer parameterOrder;
}