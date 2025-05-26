package com.kafkaconsumer.dto;

import lombok.Data;

import java.time.LocalTime;


@Data
public class WorkingHours {
    private LocalTime start;
    private LocalTime end;
}
