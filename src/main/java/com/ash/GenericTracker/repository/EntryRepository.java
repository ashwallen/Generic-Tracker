package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
}
