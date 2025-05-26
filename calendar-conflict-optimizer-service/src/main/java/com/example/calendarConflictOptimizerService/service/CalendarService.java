package com.example.calendarConflictOptimizerService.service;

import com.example.calendarConflictOptimizerService.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class CalendarService {

    public Result calendarConflictOptimizer(Calendar calendar) {
        List<Conflict> conflicts = detectConflicts(calendar.getEvents(), calendar.getTimeZone());
        LocalDate date = calendar.getEvents().get(0).getStartTime().toLocalDate();
        List<FreeSlot> freeSlots = calculateFreeSlots(date, calendar.getWorkingHours(),calendar.getTimeZone(),calendar.getEvents());
        return Result.builder().conflicts(conflicts).freeSlots(freeSlots).build();
    }
    public List<Conflict> detectConflicts(List<Event> events, String timeZone) {
        List<Conflict> conflicts = new ArrayList<>();
        Conflict conflict = null;

        for (int i = 0; i < events.size(); i++) {
            for (int j = i + 1; j < events.size(); j++) {
                Event event1 = events.get(i);
                Event event2 = events.get(j);
                OffsetDateTime start1 = event1.getStartTime();
                OffsetDateTime end1 = event1.getEndTime();
                OffsetDateTime start2 = event2.getStartTime();
                OffsetDateTime end2 = event2.getEndTime();

                if (start1.isBefore(end2) && start2.isBefore(end1)) {
                    conflict = new Conflict();
                    conflict.setEvent1(event1.getTitle());
                    conflict.setEvent2(event2.getTitle());
                    conflict.setOverlapStart(start2.atZoneSameInstant(ZoneId.of(timeZone)).toOffsetDateTime());
                    conflict.setOverlapEnd(end1.atZoneSameInstant(ZoneId.of(timeZone)).toOffsetDateTime());
                    conflicts.add(conflict);
                }
            }
        }

        return conflicts;
    }


    public List<FreeSlot> calculateFreeSlots(LocalDate date, WorkingHours workingHours,
                                             String timeZone, List<Event> events) {

        ZoneId zoneId = ZoneId.of(timeZone);

        // Convert working hours to OffsetDateTime for the specific date
        OffsetDateTime workStart = LocalDateTime.of(date, workingHours.getStart())
                .atZone(zoneId)
                .toOffsetDateTime();

        OffsetDateTime workEnd = LocalDateTime.of(date, workingHours.getEnd())
                .atZone(zoneId)
                .toOffsetDateTime();

        // Sort events by start time
        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort(Comparator.comparing(Event::getStartTime));

        List<FreeSlot> freeSlots = new ArrayList<>();
        OffsetDateTime currentTime = workStart;

        for (Event event : sortedEvents) {
            if (event.getStartTime().isAfter(currentTime)) {
                // Found a free slot
                freeSlots.add(createFreeSlot(currentTime, event.getStartTime(), timeZone));
            }
            // Move current time to the later of current time or event end time
            currentTime = currentTime.isBefore(event.getEndTime()) ?
                    event.getEndTime() : currentTime;
        }

        // Check for free time after last event
        if (currentTime.isBefore(workEnd)) {
            freeSlots.add(createFreeSlot(currentTime, workEnd, timeZone));
        }

        return freeSlots;
    }

    private FreeSlot createFreeSlot(OffsetDateTime start, OffsetDateTime end, String timeZone) {

        return FreeSlot.builder().start(start.atZoneSameInstant(ZoneId.of(timeZone)).toOffsetDateTime()).
                end(end.atZoneSameInstant(ZoneId.of(timeZone)).toOffsetDateTime()).
                build();
    }

}
