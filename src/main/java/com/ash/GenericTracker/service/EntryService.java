package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.EntryRequestDto;
import com.ash.GenericTracker.dto.EntryResponseDto;

import java.util.List;
import java.util.UUID;

public interface EntryService {
    EntryResponseDto createEntry(EntryRequestDto entry, UUID userId);

    EntryResponseDto getEntry(UUID entryId, UUID userId);

    EntryResponseDto updateEntry(UUID entryId, EntryRequestDto request, UUID userId);

    void deleteEntry(UUID entryId, UUID userId);

    List<EntryResponseDto> getEntriesByBucket(UUID bucketId, UUID userId);
}