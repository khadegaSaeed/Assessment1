package com.example.calendarConflictOptimizerService.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;


@Data
@Builder
public class FreeSlot {

    private OffsetDateTime start;
    private OffsetDateTime end;
}
