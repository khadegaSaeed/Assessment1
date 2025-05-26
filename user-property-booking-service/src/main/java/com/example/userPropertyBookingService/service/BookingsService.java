package com.example.userPropertyBookingService.service;

import com.example.userPropertyBookingService.dtos.BookingDto;

import java.time.LocalDate;
import java.util.List;


public interface BookingsService {

    List<BookingDto> getAll(Integer pageNumber, Integer pageSize);

    BookingDto getById(Long id);

    void deleteById(Long id);

    BookingDto create(BookingDto BookingDto);

    BookingDto updateById(BookingDto BookingDto, Long id);

    boolean isUserAvailable(Long userId, LocalDate startDate, LocalDate endDate);
}