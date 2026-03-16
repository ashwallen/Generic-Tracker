package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.EntryRequestDto;
import com.ash.GenericTracker.entity.Entry;

public interface EntryService {
    Entry createEntry(EntryRequestDto entry);
}