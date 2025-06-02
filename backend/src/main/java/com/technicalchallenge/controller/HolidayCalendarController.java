package com.technicalchallenge.controller;

import com.technicalchallenge.model.HolidayCalendar;
import com.technicalchallenge.service.HolidayCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/holiday-calendars")
public class HolidayCalendarController {
    private static final Logger logger = LoggerFactory.getLogger(HolidayCalendarController.class);

    @Autowired
    private HolidayCalendarService holidayCalendarService;

    @GetMapping
    public List<HolidayCalendar> getAll() {
        logger.info("Fetching all holiday calendars");
        return holidayCalendarService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HolidayCalendar> getById(@PathVariable Long id) {
        logger.debug("Fetching holiday calendar by id: {}", id);
        return holidayCalendarService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HolidayCalendar create(@RequestBody HolidayCalendar holidayCalendar) {
        logger.info("Creating new holiday calendar: {}", holidayCalendar);
        return holidayCalendarService.save(holidayCalendar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HolidayCalendar> update(@PathVariable Long id, @RequestBody HolidayCalendar holidayCalendar) {
        return holidayCalendarService.findById(id)
                .map(existing -> {
                    holidayCalendar.setId(id);
                    return ResponseEntity.ok(holidayCalendarService.save(holidayCalendar));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting holiday calendar with id: {}", id);
        if (holidayCalendarService.findById(id).isPresent()) {
            holidayCalendarService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
