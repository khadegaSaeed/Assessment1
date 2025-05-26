package com.example.userPropertyBookingService.controller;

import com.example.userPropertyBookingService.dtos.BookingDto;
import com.example.userPropertyBookingService.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingsService service;


    @GetMapping(path = "/{id}")
    public ResponseEntity<BookingDto> getBookingsById(@PathVariable Long id)
    {
        BookingDto bookings = service.getById(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<BookingDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize)
    {
        List<BookingDto> allBookings = service.getAll(pageNumber, pageSize);
        if (!allBookings.isEmpty())
            return new ResponseEntity<>(allBookings, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/")
    public ResponseEntity<BookingDto> addBookings(@RequestBody BookingDto bookingsDTO)
    {
        BookingDto bookings = service.create(bookingsDTO);

        if (bookings != null)
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookingDto> updateBookings(@PathVariable Long id, @RequestBody BookingDto newBookings)
    {
        BookingDto bookings = service.updateById(newBookings,id);

        if (bookings != null)
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/checkUserAvailability")
    public ResponseEntity<?> checkUserAvailability(
            @RequestParam Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        boolean isAvailable = service.isUserAvailable(userId, startDate, endDate);
        return ResponseEntity.ok(isAvailable);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteBookingById(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}