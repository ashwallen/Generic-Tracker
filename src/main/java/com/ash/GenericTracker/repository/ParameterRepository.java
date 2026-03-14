package com.ash.GenericTracker.repository;

import com.ash.GenericTracker.entity.Bucket;
import com.ash.GenericTracker.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
    List<Parameter>findByBucketIdAndIsActiveTrueOrderByParameterOrder(Bucket bucket);
}
