package com.example.userPropertyBookingService.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {
    private Long id;

    private String title;

    private UserDto owner;

}