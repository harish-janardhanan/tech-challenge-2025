package com.technicalchallenge.repository;

import com.technicalchallenge.model.LegType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegTypeRepository extends JpaRepository<LegType, Long> {
    // Custom query methods if needed
}
