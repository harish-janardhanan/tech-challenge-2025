package com.technicalchallenge.repository;

import com.technicalchallenge.model.Counterparty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterpartyRepository extends JpaRepository<Counterparty, Long> {
    // Custom query methods if needed
}
