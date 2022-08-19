package com.configuration;

import com.configuration.utils.GenerateUtils;
import com.entity.Seat;
import com.entity.User;
import com.entity.assignmentrequirements.Booking;
import com.entity.assignmentrequirements.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class FlightSupplier {
    public static FlightSupplier supplier = new FlightSupplier();
    private final Random random = new Random();

    public Supplier<Flight> flightSupplier = () -> {
        Flight flight = new Flight();
        String city1 = GenerateUtils.generateRandomString(Math.abs(random.nextInt()%20) + 2);
        String city2 = GenerateUtils.generateRandomString(Math.abs(random.nextInt()%20) + 2);
        flight.setFlightName(city1 + " to " + city2);
        flight.setCity1(city1);
        flight.setCity2(city2);
        flight.setFlightType(Math.abs(random.nextInt()%3) == 1 ? "Domestic" : "International");
        return flight;
    };

    public BiFunction<Flight, User, Flight> bookingSupplier = (flight, user) -> {
        Booking booking = new Booking();
        LocalDate randomDate = LocalDate.now().plus(Period.ofDays((new Random().nextInt(365 * 70))));
        booking.setDateOfBooking(Date.from(randomDate.atStartOfDay().toInstant(ZoneOffset.UTC)));
        if(flight.getBookingList() != null) {
            flight.getBookingList().add(booking);
        } else {
            flight.setBookingList(new ArrayList<>());
        }
        return flight;
    };
}
