package com.ash.GenericTracker.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryRequestDto {
    private UUID bucketId;
    private String notes;
    private String Date;
}
