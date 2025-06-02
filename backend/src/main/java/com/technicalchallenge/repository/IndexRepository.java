package com.technicalchallenge.repository;

import com.technicalchallenge.model.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexRepository extends JpaRepository<Index, Long> {
    // Custom query methods if needed
}
