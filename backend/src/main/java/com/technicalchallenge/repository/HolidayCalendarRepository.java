package com.technicalchallenge.repository;

import com.technicalchallenge.model.HolidayCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayCalendarRepository extends JpaRepository<HolidayCalendar, Long> {
    // Custom query methods if needed
}
