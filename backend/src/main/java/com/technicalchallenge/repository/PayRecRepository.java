package com.technicalchallenge.repository;

import com.technicalchallenge.model.PayRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRecRepository extends JpaRepository<PayRec, Long> {
    // Custom query methods if needed
}
