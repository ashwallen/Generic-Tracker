package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.entity.EntryValue;
import com.ash.GenericTracker.entity.Parameter;
import com.ash.GenericTracker.exception.CustomExceptionHandler;
import com.ash.GenericTracker.repository.EntryRepository;
import com.ash.GenericTracker.repository.EntryValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntryService {
    private EntryRepository entryRepository;
    private EntryValueRepository entryValueRepository;
    public ApiResponse createEntry(Entry entry) {
        if(!isValidDate()){
            throw new CustomExceptionHandler("Enter Valid Date");
        }
        try {
            entryRepository.save(entry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(true,"Inserted Entry Successfully",1);
    }

    private boolean isValidDate() {
        return false;
    }

    public ApiResponse createEntryValue(EntryValue entryValue) {
        try {
           entryExist(entryValue.getEntry());
           paramExist(entryValue.getParameter());
           if(isValidType(entryValue)){
               entryValueRepository.save(entryValue);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(true,"Inserted EntryValue Successfully",1);
    }

    private void entryExist(Entry entry) {
    }

    private void paramExist(Parameter parameter) {
    }

    private boolean isValidType(EntryValue entryValue) {
        return false;
    }
}
