package com.example.userPropertyBookingService.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto implements Serializable {

    private Long id;


    private PropertyDto property;


    private UserDto user;


    private LocalDate startDate;


    private LocalDate endDate;

}