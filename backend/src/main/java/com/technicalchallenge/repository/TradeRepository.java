package com.technicalchallenge.repository;

import com.technicalchallenge.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    // Custom query methods if needed
}
