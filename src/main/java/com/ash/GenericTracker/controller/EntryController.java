package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.entity.EntryValue;
import com.ash.GenericTracker.service.EntryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class EntryController {
    private EntryService entryService;
    @PostMapping("/create/entryValue")
    ResponseEntity<ApiResponse> createEntryValue(Entry data){
        ApiResponse response = entryService.createEntry(data);
        return ResponseEntity.ok(response);
    }

}
