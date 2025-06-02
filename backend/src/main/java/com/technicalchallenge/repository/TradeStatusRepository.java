package com.technicalchallenge.repository;

import com.technicalchallenge.model.TradeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeStatusRepository extends JpaRepository<TradeStatus, Long> {
    // Custom query methods if needed
}
