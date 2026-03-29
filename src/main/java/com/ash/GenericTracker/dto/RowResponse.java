package com.ash.GenericTracker.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RowResponse {

    private UUID rowId;
    private Integer rowIndex;
    private List<ValueResponse> values;
}