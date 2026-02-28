package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, UUID> {

}
