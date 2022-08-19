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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("The add passenger method should insert the entity")
    void addPassengerShouldInsert() throws EntityNotFoundException {
        Long userId = 1L;
        Long flightId = 1L;

        Flight flight = new Flight();
        flight.setCity1("city1");
        flight.setCity2("city2");
        User user = new User();
        user.setUserId(userId);
        user.setPassenger(new Passenger());
        user.getPassenger().setPassengerDetails(new PassengerDetails());

        Mockito.when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        AdminBookingInsertDTO adminBookingInsertDTO = new AdminBookingInsertDTO();
        adminBookingInsertDTO.setUserId(userId);

        AdminBookingResponseDTO adminBookingResponseDTO = bookingService.addPassangerToFlight(adminBookingInsertDTO, flightId);
        assertEquals("FROM:city1 , TO: city2", adminBookingResponseDTO.getFlightFromTo());
    }

    @Test
    @DisplayName("Throw exception when create passenger to flight")
    public void shouldThrowExceptionAddPassengerToFlight() {
        assertThatThrownBy(() -> bookingService.addPassangerToFlight(new AdminBookingInsertDTO(), 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("The getFlightPassengerBookingList method should return")
    public void getFlightPassengerBookingListShouldReturn() throws EntityNotFoundException {
        User user = new User();
        user.setUserId(1L);
        user.setPassenger(new Passenger());
        user.getPassenger().setPassengerDetails(new PassengerDetails());
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(new Booking(1L, Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))));
        bookingList.add(new Booking(2L, Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))));
        user.getPassenger().getAllBookings().addAll(bookingList);
        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        flight.setCity1("city1");
        flight.setCity2("city2");
        flight.getBookingList().addAll(bookingList);
        flights.add(flight);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(flightRepository.findFlightsByBookingIds(Arrays.asList(1L, 2L))).thenReturn(flights);

        AdminBookingInsertDTO adminBookingInsertDTO = new AdminBookingInsertDTO();
        //adminBookingInsertDTO.setFullName("D");
        ListWrapper<List<AdminBookingResponseDTO>> listWrapper = bookingService.getFlightPassengerBookingList(1L);
        List<AdminBookingResponseDTO> adminBookingResponseDTOS = listWrapper.getList();

        assertEquals(2, adminBookingResponseDTOS.size());
        //assertIterableEquals();
    }

    @Test
    @DisplayName("The getFlightPassengerBookingList method should throw")
    public void getFlightPassengerBookingListShouldThrow() {
        assertThatThrownBy(() -> bookingService.getFlightPassengerBookingList(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }


}