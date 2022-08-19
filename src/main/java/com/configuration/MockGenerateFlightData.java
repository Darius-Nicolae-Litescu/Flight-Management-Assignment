package com.configuration;

import com.dto.request.insert.FlightInsertDTO;
import com.dto.request.insert.assignmentrequirements.AdminBookingInsertDTO;
import com.entity.User;
import com.entity.assignmentrequirements.Flight;
import com.exception.EntityNotFoundException;
import com.service.FlightService;
import com.service.assignmentrequirements.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MockGenerateFlightData {
    private FlightService flightService;
    private final BookingService bookingService;
    private final FlightSupplier supplier = FlightSupplier.supplier;

    List<Flight> insertedFlights = new ArrayList<>();

    @Autowired
    public MockGenerateFlightData(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setFlightService(FlightService flightService) {
        this.flightService = flightService;
    }

    public void addFlight(User user) throws EntityNotFoundException {

        for (int i = 0; i < Math.abs(new Random().nextInt() % 2) + 1; i++) {
            Flight flight = supplier.flightSupplier.get();
            FlightInsertDTO flightInsertDTO = new FlightInsertDTO();
            flightInsertDTO.setFlightName(flight.getFlightName());
            flightInsertDTO.setCity1(flight.getCity1());
            flightInsertDTO.setCity2(flight.getCity2());
            flightInsertDTO.setFlightType(flight.getFlightType());

            flight.setFlightId(flightService.addFlight(flightInsertDTO).getFlightNumber());
            insertedFlights.add(flight);
        }
        for (Flight flight : insertedFlights) {
            flight = supplier.bookingSupplier.apply(flight, user);
            AdminBookingInsertDTO adminBookingInsertDTO = new AdminBookingInsertDTO();
            adminBookingInsertDTO.setUserId(user.getUserId());
            adminBookingInsertDTO.setGender(user.getPassenger().getPassengerDetails().getGender());
            adminBookingInsertDTO.setPhoneNumber(user.getPassenger().getPassengerDetails().getPhoneNumber());
            String firstName = user.getPassenger().getPassengerDetails().getFirstName();
            String lastName = user.getPassenger().getPassengerDetails().getLastName();
            adminBookingInsertDTO.setFullName(firstName + " " + lastName);
            bookingService.addPassengerWithRandomBooking(adminBookingInsertDTO, flight.getFlightId());

        }

    }
}
