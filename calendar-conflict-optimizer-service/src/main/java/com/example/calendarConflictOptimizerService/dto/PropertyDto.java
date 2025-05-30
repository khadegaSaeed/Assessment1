package com.example.calendarConflictOptimizerService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {
    private Long id;

    private String title;

    private UserDto owner;

}