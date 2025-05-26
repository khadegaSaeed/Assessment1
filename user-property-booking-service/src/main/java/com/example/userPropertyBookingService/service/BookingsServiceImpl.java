package com.example.userPropertyBookingService.service;

import com.example.userPropertyBookingService.dtos.BookingDto;
import com.example.userPropertyBookingService.dtos.PropertyDto;
import com.example.userPropertyBookingService.entities.Booking;
import com.example.userPropertyBookingService.exception.AlreadyExistsException;
import com.example.userPropertyBookingService.exception.ResourceNotFoundException;
import com.example.userPropertyBookingService.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookingsServiceImpl implements BookingsService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PropertiesService propertyService;


    @Override
    public List<BookingDto> getAll(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Booking> returnedBooking =  bookingRepository.findAll(pageable);
        if (!returnedBooking.isEmpty()) {
            return returnedBooking.stream()
                    .map(entity -> modelMapper.map(entity, BookingDto.class))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Booking is Empty", " ", null);
        }
    }


    @Override
    @Cacheable(value = "Booking", key = "#id")
    public BookingDto getById(Long id) {
        Optional<Booking> returnedBooking =
                bookingRepository.findById(id);
        if (returnedBooking.isPresent()) {
            return modelMapper.map(returnedBooking.get(),
                    BookingDto.class);
        } else {
            throw new ResourceNotFoundException("Booking", "ID", id);
        }
    }


    @Override
    @CacheEvict(value = "Booking", key = "#id")
    public void deleteById(Long id) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "Booking", key = "#result.id")
    public BookingDto create(BookingDto bookingdto) {
        // Validate dates
        if (bookingdto.getStartDate().isAfter(bookingdto.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (bookingdto.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date must be in the future");
        }

        // Get property and user
        PropertyDto property = propertyService.getById(bookingdto.getProperty().getId());

        if (property == null) {
            throw new IllegalArgumentException("Property not found");
        }

        // Check availability to Prevent overlapping bookings
        if (!isUserAvailable(bookingdto.getUser().getId(), bookingdto.getStartDate(), bookingdto.getEndDate())) {
            throw new AlreadyExistsException("User is already booked for the selected dates");
        }

        Booking booking = modelMapper.map(bookingdto,
                Booking.class);

        Booking returnedBooking =
                bookingRepository.save(booking);

        return modelMapper.map(returnedBooking,
                BookingDto.class);
    }

    @Override
    @CachePut(value = "Booking", key = "#id")
    public BookingDto updateById(
            BookingDto bookingDTO, Long id) {
        Booking booking =
                modelMapper.map(bookingDTO, Booking.class);

        bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        booking.setId(id);

        Booking reTurnedBooking =
                bookingRepository.save(booking);

        return modelMapper.map(reTurnedBooking,
                BookingDto.class);
    }

    public boolean isUserAvailable(Long userId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        List<Booking> conflictingBookings = bookingRepository.conflictingBookings(
                userId,
                startDate,
                endDate);

        return conflictingBookings.isEmpty();
    }
}
