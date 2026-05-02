package com.ash.GenericTracker.controller;

import com.ash.GenericTracker.dto.*;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.service.EntryService;
import com.ash.GenericTracker.service.EntryValueService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entries")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;
    private final EntryValueService entryValueService;
    @PostMapping("/create")
    ResponseEntity<ApiResponse<EntryResponseDto>> createEntry(@RequestBody EntryRequestDto entry, Authentication authentication){
        UUID userId = UUID.fromString(authentication.getName());
        EntryResponseDto response = entryService.createEntry(entry,userId);
        ApiResponse<EntryResponseDto>apiResponse = ApiResponse.<EntryResponseDto>builder()
                .success(true)
                .data(response)
                .message("Created Entry Successfully")
                .status(201).build();
        return ResponseEntity.status(201).body(apiResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<EntryResponseDto>> deleteEntry(@RequestBody DeleteEntry entry,Authentication authentication){
        UUID userId = UUID.fromString(authentication.getName());
        UUID entryId = entry.getEntryId();
        if(entryId == null || userId==null){
            throw new RuntimeException("entryId is Null, cannot delete entry");
        }
        entryService.deleteEntry(entryId,userId);
        return ResponseEntity.ok(new ApiResponse<>(true,"deleted Successfully",null,204));
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

    @GetMapping("/{entryId}/details")
    public ResponseEntity<ApiResponse<EntryDetailResponse>> getEntryDetails(
            @PathVariable UUID entryId,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        EntryDetailResponse response =
                entryValueService.getEntryDetails(entryId, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Entry details fetched", response, 200)
        );
    }

    @PostMapping("/insert/rows")
    public ResponseEntity<ApiResponse<Void>> saveRows(
            @RequestBody EntryValueRequest request,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        entryValueService.saveEntryRows(request, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Rows saved successfully", null, 200)
        );
    }
    @DeleteMapping("/delete/row/{rowId}")
    public ResponseEntity<ApiResponse<Void>> deleteRow(
            @PathVariable UUID rowId,
            Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        entryValueService.deleteRow(rowId, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Row deleted successfully", null, 200)
        );
    }
}
