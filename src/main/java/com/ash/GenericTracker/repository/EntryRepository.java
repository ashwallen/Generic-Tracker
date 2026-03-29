package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    boolean existsByBucketId_IdAndEntryDate(UUID bucketId, LocalDate entryDate);

    Optional<Entry> findByIdAndUserId_Id(UUID entryId, UUID userId);

    List<Entry> findByBucketId_IdAndUserId_IdOrderByEntryDateDesc(UUID bucketId, UUID userId);
}
