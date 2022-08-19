package com.service.assignmentrequirements;

import com.dto.request.insert.assignmentrequirements.AdminBookingInsertDTO;
import com.dto.response.assignmentrequirements.AdminBookingResponseDTO;
import com.entity.assignmentrequirements.Booking;
import com.exception.EntityNotFoundException;
import com.wrapper.ListWrapper;

import javax.transaction.Transactional;
import java.util.List;

public interface BookingService {
    @Transactional
    AdminBookingResponseDTO addPassangerToFlight(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber) throws EntityNotFoundException;

    @Transactional
    ListWrapper<List<AdminBookingResponseDTO>> getFlightPassengerBookingList(Long userId) throws EntityNotFoundException;

    @Transactional
    AdminBookingResponseDTO addPassengerWithRandomBooking(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber)
            throws EntityNotFoundException;
}
