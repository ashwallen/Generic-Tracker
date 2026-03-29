package com.ash.GenericTracker.dto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class EntryDetailResponse {

    private UUID entryId;
    private List<ParameterResponse> parameters;
    private List<RowResponse> rows;
}