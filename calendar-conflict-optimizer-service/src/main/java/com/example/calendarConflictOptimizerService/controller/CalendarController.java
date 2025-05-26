package com.example.calendarConflictOptimizerService.controller;

import com.example.calendarConflictOptimizerService.config.kafka.KafkaSender;
import com.example.calendarConflictOptimizerService.dto.Calendar;
import com.example.calendarConflictOptimizerService.dto.Result;
import com.example.calendarConflictOptimizerService.service.CalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class CalendarController {
    @Autowired
    public KafkaSender kafkaSender;
    @Autowired
    CalendarService calendarService;

    @PostMapping("/optimizer")
    public ResponseEntity<Result> optimizer(@RequestBody Calendar calendar) {
        Result result = calendarService.calendarConflictOptimizer(calendar);

        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sendKafka")
    public ResponseEntity changeSimCard(@RequestBody Calendar calendar) {
        kafkaSender.send(calendar);
        return ResponseEntity.ok("kafka Send Successfully");
    }
    
}