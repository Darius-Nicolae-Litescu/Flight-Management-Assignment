package com.service.assignmentrequirements;

import com.dto.request.insert.assignmentrequirements.AdminBookingInsertDTO;
import com.dto.response.assignmentrequirements.AdminBookingResponseDTO;
import com.entity.User;
import com.entity.assignmentrequirements.Booking;
import com.entity.assignmentrequirements.Flight;
import com.entity.assignmentrequirements.Passenger;
import com.entity.assignmentrequirements.PassengerDetails;
import com.exception.EntityNotFoundException;
import com.repository.BookingRepository;
import com.repository.FlightRepository;
import com.repository.UserRepository;
import com.wrapper.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AdminBookingResponseDTO addPassangerToFlight(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber) throws EntityNotFoundException {
        logger.info("Adding passenger: {} | to flight: {}", adminBookingInsertDTO, flightNumber);

        Optional<Flight> flightOptional = flightRepository.findById(flightNumber);
        if (!flightOptional.isPresent()) {
            throw new EntityNotFoundException(flightNumber);
        }
        Optional<User> userOptional = userRepository.findById(adminBookingInsertDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(adminBookingInsertDTO.getUserId());
        }
        Flight flight = flightOptional.get();
        Date currentDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Booking booking = new Booking(currentDate);

        bookingRepository.save(booking);
        flight.getBookingList().add(booking);
        flightRepository.save(flight);

        userOptional.get().getPassenger().getAllBookings().add(booking);

        logger.info("Added passenger sucesfully to flight {}", flightNumber);


        AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
        adminBookingResponseDTO.setFullName(adminBookingInsertDTO.getFullName());
        adminBookingResponseDTO.setDateOfBooking(currentDate);
        adminBookingResponseDTO.setFlightFromTo("FROM:" + flight.getCity1() + " , TO: " + flight.getCity2());
        return adminBookingResponseDTO;
    }

    @Override
    public ListWrapper<List<AdminBookingResponseDTO>> getFlightPassengerBookingList(Long userId) throws EntityNotFoundException {
        logger.info("Retrieving passenger booking list for user id: {}", userId);

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(userId);
        }
        List<Booking> bookingList = userOptional.get().getPassenger().getAllBookings();
        List<Long> bookingIds = bookingList.stream().map(
                (Booking::getBookingId)).collect(Collectors.toList());

        List<Flight> flightList = flightRepository.findFlightsByBookingIds(bookingIds);

        List<AdminBookingResponseDTO> adminBookingResponseDTOS = new ArrayList<>();
        Passenger passenger = userOptional.get().getPassenger();
        for (Flight flight : flightList) {
            for (Booking booking : bookingList) {
                AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
                if (passenger != null && passenger.getPassengerDetails() != null) {
                    PassengerDetails passengerDetails = passenger.getPassengerDetails();
                    adminBookingResponseDTO.setFullName(passengerDetails.getFirstName() + " " + passengerDetails.getLastName());
                }
                adminBookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
                adminBookingResponseDTO.setFlightFromTo(flight.getFlightName());
                adminBookingResponseDTOS.add(adminBookingResponseDTO);
            }
        }

        logger.info("Retrieved booking information sucesfully: {}", adminBookingResponseDTOS);

        Long numberOfResults = Long.valueOf(adminBookingResponseDTOS.size());
        return new ListWrapper<>(numberOfResults, adminBookingResponseDTOS);

    }

    @Override
    @Transactional
    public AdminBookingResponseDTO addPassengerWithRandomBooking(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber)
            throws EntityNotFoundException {
        logger.info("Adding passenger: {} | to flight: {}", adminBookingInsertDTO, flightNumber);

        Optional<Flight> flightOptional = flightRepository.findById(flightNumber);
        if (!flightOptional.isPresent()) {
            throw new EntityNotFoundException(flightNumber);
        }
        Optional<User> userOptional = userRepository.findById(adminBookingInsertDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(adminBookingInsertDTO.getUserId());
        }
        Flight flight = flightOptional.get();
        LocalDate randomDate = LocalDate.now().plus(Period.ofDays((new Random().nextInt(365 * 70))));
        Booking booking = new Booking(Date.from(randomDate.atStartOfDay().toInstant(ZoneOffset.UTC)));

        bookingRepository.save(booking);
        flight.getBookingList().add(booking);
        flightRepository.save(flight);

        userOptional.get().getPassenger().getAllBookings().add(booking);

        logger.info("Added passenger sucesfully to flight {}", flightNumber);


        AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
        adminBookingResponseDTO.setFullName(adminBookingInsertDTO.getFullName());
        adminBookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
        adminBookingResponseDTO.setFlightFromTo("FROM:" + flight.getCity1() + " , TO: " + flight.getCity2());
        return adminBookingResponseDTO;
    }

}
