package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
    List<Parameter> findByBucketId_IdAndIsActiveTrueOrderByParameterOrder(UUID bucketId);
}
