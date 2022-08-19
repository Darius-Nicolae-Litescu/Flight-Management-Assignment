package com.repository;

import com.entity.assignmentrequirements.Booking;
import com.entity.assignmentrequirements.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


}
