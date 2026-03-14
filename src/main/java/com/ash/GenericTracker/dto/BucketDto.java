package com.ash.GenericTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDto {
    @NotNull
    private UUID id;
    @NotBlank
    private String name;
    private String description;
    private List<ParameterDto> parameters;
}
