package com.ash.GenericTracker.dto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EntryValueRequest {

    private UUID entryId;

    private List<RowInput> rows;
}