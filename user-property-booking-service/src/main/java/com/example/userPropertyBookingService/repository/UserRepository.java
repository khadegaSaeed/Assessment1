package com.example.userPropertyBookingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userPropertyBookingService.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
