package com.example.calendarConflictOptimizerService.dto;

import lombok.Data;

import java.time.OffsetDateTime;


@Data
public class Event {
        private String title;

        private OffsetDateTime startTime;

        private OffsetDateTime endTime;
}
