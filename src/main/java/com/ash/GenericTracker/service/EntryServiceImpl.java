package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.EntryRequestDto;
import com.ash.GenericTracker.entity.Entry;

public class EntryServiceImpl extends EntryService {

    @Override
    public Entry createEntry(EntryRequestDto entry) {
        //validate Date, notes should not be empty, valid bucket id and valid userId.
    }
}
