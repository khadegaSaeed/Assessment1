package com.example.userPropertyBookingService;

import com.example.userPropertyBookingService.dtos.BookingDto;
import com.example.userPropertyBookingService.dtos.PropertyDto;
import com.example.userPropertyBookingService.dtos.UserDto;
import com.example.userPropertyBookingService.entities.Booking;
import com.example.userPropertyBookingService.entities.Property;
import com.example.userPropertyBookingService.repository.BookingRepository;
import com.example.userPropertyBookingService.repository.PropertyRepository;
import com.example.userPropertyBookingService.service.BookingsService;
import com.example.userPropertyBookingService.service.PropertiesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PropertyBookingTest extends BasePostgresTestContainer {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingsService bookingsService;

    @Test
    void shouldSaveAndRetrieveProperty() {

        // Act
        PropertyDto savedProperty = propertiesService.create(getPropertyDto());
        Property retrievedProperty = propertyRepository.findById(savedProperty.getId()).get();

        // Assert
        assertThat(retrievedProperty).isNotNull();
        assertThat(retrievedProperty.getId()).isNotNull();
    }

    @Test
    void shouldSaveAndRetrieveBooking() {
        // Act
        BookingDto savedBooking = bookingsService.create(getBookingDto());
        Booking retrievedBooking = bookingRepository.findById(savedBooking.getId()).get();

        // Assert
        assertThat(savedBooking).isNotNull();
        assertThat(retrievedBooking.getId()).isNotNull();
    }

    @Test
    void isUserAvailable() {

        Long userId = 5L;
        LocalDate testStart1 = LocalDate.now().plusDays(1);
        LocalDate testEnd1 = LocalDate.now().plusDays(4);

        assertEquals(true, bookingsService.isUserAvailable(userId, testStart1, testEnd1), "User should be available for non-overlapping dates");
    }


    private PropertyDto getPropertyDto(){
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setTitle("Mountain22 Cabin");
        UserDto owner = new UserDto();
        owner.setId(2L);
        propertyDto.setOwner(owner);
        return  propertyDto;
    }
    private BookingDto getBookingDto(){
        UserDto userDto = new UserDto();
        userDto.setId(5L);

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(1L);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setStartDate(LocalDate.now().plusDays(7));
        bookingDto.setEndDate(LocalDate.now().plusDays(14));
        bookingDto.setUser(userDto);
        bookingDto.setProperty(propertyDto);
        return bookingDto;
    }
}