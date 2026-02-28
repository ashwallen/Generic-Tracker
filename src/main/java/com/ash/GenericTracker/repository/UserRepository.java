package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
