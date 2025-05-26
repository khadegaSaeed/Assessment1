package com.example.calendarConflictOptimizerService.dto;

import lombok.Data;

import java.time.OffsetDateTime;


@Data
public class Conflict {
    private String event1;
    private String event2;
    private OffsetDateTime overlapStart;
    private OffsetDateTime overlapEnd;
}
