package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.EntryRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EntryRowRepository extends JpaRepository<EntryRow, UUID> {

    List<EntryRow> findByEntryIdOrderByRowIndex(UUID entryId);
}