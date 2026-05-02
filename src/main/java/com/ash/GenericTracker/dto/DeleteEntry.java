package com.ash.GenericTracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteEntry {
    private UUID entryId;
}
