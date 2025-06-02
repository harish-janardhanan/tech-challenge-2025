package com.technicalchallenge.repository;

import com.technicalchallenge.model.TradeSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeSubTypeRepository extends JpaRepository<TradeSubType, Long> {
    // Optionally add custom query methods here
}
