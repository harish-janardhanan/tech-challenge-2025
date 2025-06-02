package com.technicalchallenge.repository;

import com.technicalchallenge.model.BusinessDayConvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessDayConventionRepository extends JpaRepository<BusinessDayConvention, Long> {
    // Custom query methods if needed
}
