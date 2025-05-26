package com.example.calendarConflictOptimizerService.dto;

import lombok.Data;

import java.util.List;


@Data
public class Calendar {
    private WorkingHours workingHours;

    private String timeZone;

    private List<Event> events;
}
