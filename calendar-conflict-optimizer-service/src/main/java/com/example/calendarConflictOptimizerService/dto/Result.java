package com.example.calendarConflictOptimizerService.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class Result {

    private List<Conflict> conflicts;
    private List<FreeSlot> freeSlots;

}
