package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.dto.EntryRequestDto;
import com.ash.GenericTracker.dto.EntryResponseDto;
import com.ash.GenericTracker.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/entries")
@RequiredArgsConstructor
public class EntryController {
    private EntryService entryService;
    @PostMapping("/create")
    ResponseEntity<ApiResponse<EntryResponseDto>> createEntryValue
            (@RequestBody EntryRequestDto entry, Authentication authentication){
        UUID userId = UUID.fromString(authentication.getName());
        EntryResponseDto response = entryService.createEntry(entry,userId);
        ApiResponse<EntryResponseDto>apiResponse = ApiResponse.<EntryResponseDto>builder()
                .success(true)
                .data(response)
                .message("Created Entry Successfully")
                .status(201).build();
        return ResponseEntity.status(201).body(apiResponse);
    }
    @GetMapping("/bucket/{bucketId}")
    public ResponseEntity<ApiResponse<List<EntryResponseDto>>> getEntriesByBucket(
            @PathVariable UUID bucketId,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        List<EntryResponseDto> entries = entryService.getEntriesByBucket(bucketId, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Entries fetched", entries, 200)
        );
    }
}
