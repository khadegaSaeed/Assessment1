package com.example.userPropertyBookingService.repository;

import com.example.userPropertyBookingService.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
//    List<Booking> findByUserId(Long userId);
//
//    List<Booking> findByPropertyId(Long propertyId);
//
//    @Query("SELECT b FROM Booking b WHERE b.property.owner.id = :ownerId")
//    List<Booking> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.user.id = :userId" +
            " AND NOT (b.endDate < :startDate OR b.startDate > :endDate)")
    List<Booking> conflictingBookings(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


}