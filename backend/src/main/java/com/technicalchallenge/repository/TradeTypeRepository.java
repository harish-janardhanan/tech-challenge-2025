package com.technicalchallenge.repository;

import com.technicalchallenge.model.TradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeTypeRepository extends JpaRepository<TradeType, Long> {
    // Custom query methods if needed
}
