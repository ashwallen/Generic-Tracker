package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.EntryValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntryValueRepository extends JpaRepository<EntryValue, UUID> {
}
