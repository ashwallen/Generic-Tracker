package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.EntryDetailResponse;
import com.ash.GenericTracker.dto.EntryValueRequest;
import com.ash.GenericTracker.dto.ParameterDto;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.entity.Parameter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface EntryValueService {
    EntryDetailResponse getEntryDetails(UUID entryId, UUID userId);
    void saveEntryRows(EntryValueRequest request, UUID userId);
}
