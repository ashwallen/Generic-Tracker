package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.EntryRequestDto;
import com.ash.GenericTracker.dto.EntryResponseDto;
import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.repository.BucketRepository;
import com.ash.GenericTracker.repository.EntryRepository;
import com.ash.GenericTracker.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;
    @Override
    public EntryResponseDto createEntry(EntryRequestDto request, UUID userId) {
        LocalDate entryDate = LocalDate.parse(request.getDate());

        // 1️⃣ Validate bucket
        Bucket bucket = bucketRepository.findById(request.getBucketId())
                .orElseThrow(() -> new RuntimeException("Bucket not found"));
        // 2️⃣ Validate ownership
        if (!bucket.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to bucket");
        }

        // 3️⃣ Validate date
        if (entryDate == null) {
            throw new RuntimeException("Entry date is required");
        }

        if (entryDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Entry date cannot be in future");
        }

        // 4️⃣ One entry per day validation
        boolean exists = entryRepository.existsByBucketIdAndEntryDate(
                bucket.getId(),
                entryDate
        );

        if (exists) {
            throw new RuntimeException("Entry already exists for this date");
        }

        // 5️⃣ Create entry
        Entry entry = Entry.builder()
                .bucketId(bucket)
                .userId(bucket.getUser())
                .entryDate(entryDate)
                .notes(request.getNotes())
                .build();

        Entry savedEntry = entryRepository.save(entry);

        // 6️⃣ Map to response
        return EntryResponseDto.builder()
                .entryId(savedEntry.getId())
                .bucketId(bucket.getId())
                .entryDate(savedEntry.getEntryDate())
                .notes(savedEntry.getNotes())
                .build();
    }

    @Override
    public EntryResponseDto getEntry(UUID entryId, UUID userId) {

        Entry entry = entryRepository.findByIdAndUserId(entryId, userId)
                .orElseThrow(() -> new RuntimeException("Entry not found or unauthorized"));

        return EntryResponseDto.builder()
                .entryId(entry.getId())
                .bucketId(entry.getBucketId().getId())
                .entryDate(entry.getEntryDate())
                .notes(entry.getNotes())
                .build();
    }

    @Override
    public EntryResponseDto updateEntry(UUID entryId, EntryRequestDto request, UUID userId) {

        Entry entry = entryRepository.findByIdAndUserId(entryId, userId)
                .orElseThrow(() -> new RuntimeException("Entry not found or unauthorized"));

        LocalDate entryDate = LocalDate.parse(request.getDate());

        // Validate date
        if (entryDate== null) {
            throw new RuntimeException("Entry date is required");
        }

        if (entryDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Entry date cannot be in future");
        }

        // If date changed → check duplicate
        if (!entry.getEntryDate().equals(entryDate)) {

            boolean exists = entryRepository.existsByBucketIdAndEntryDate(
                    entry.getBucketId().getId(),
                    entryDate
            );

            if (exists) {
                throw new RuntimeException("Entry already exists for this date");
            }
        }

        // Update fields
        entry.setEntryDate(entryDate);
        entry.setNotes(request.getNotes());

        Entry updated = entryRepository.save(entry);

        return EntryResponseDto.builder()
                .entryId(updated.getId())
                .bucketId(updated.getBucketId().getId())
                .entryDate(updated.getEntryDate())
                .notes(updated.getNotes())
                .build();
    }

    @Override
    public void deleteEntry(UUID entryId, UUID userId) {
        userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Does Not Exist"));
        Entry entry = entryRepository.findByIdAndUserId(entryId,userId).orElseThrow(()->new RuntimeException("Entry Does not Exist"));
        entryRepository.delete(entry);
    }

    @Override
    public List<EntryResponseDto> getEntriesByBucket(UUID bucketId, UUID userId) {
        List<Entry>entries = entryRepository.findByBucketIdAndUserIdOrderByEntryDateDesc(bucketId,userId);
        return entries.stream()
                .map( entry -> EntryResponseDto.builder()
                        .bucketId(entry.getBucketId().getId())
                        .entryDate(entry.getEntryDate())
                        .notes(entry.getNotes())
                        .entryId(entry.getId()).build()
                ).toList();
    }

}
