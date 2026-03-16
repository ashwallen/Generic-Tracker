package com.ash.GenericTracker.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryResponseDto {
    private UUID entryId;

    private UUID bucketId;

    private LocalDate entryDate;

    private String notes;
}
