package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    boolean existsByBucketIdAndEntryDate(UUID bucketId, LocalDate entryDate);

    Optional<Entry> findByIdAndUserId(UUID entryId, UUID userId);

    List<Entry> findByBucketIdAndUserIdOrderByEntryDateDesc(UUID bucketId, UUID userId);
}
