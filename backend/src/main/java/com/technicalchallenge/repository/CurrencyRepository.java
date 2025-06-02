package com.technicalchallenge.repository;

import com.technicalchallenge.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    // Custom query methods if needed
}
