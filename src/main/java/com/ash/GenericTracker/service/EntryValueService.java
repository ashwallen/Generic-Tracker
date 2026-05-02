package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.EntryDetailResponse;
import com.ash.GenericTracker.dto.EntryValueRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface EntryValueService {
    EntryDetailResponse getEntryDetails(UUID entryId, UUID userId);
    void saveEntryRows(EntryValueRequest request, UUID userId);
    void deleteRow(UUID rowId, UUID userId);
}
