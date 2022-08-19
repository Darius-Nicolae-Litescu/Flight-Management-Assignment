package com.service.assignmentrequirements;

import com.dto.request.insert.assignmentrequirements.PassengerInsertDTO;
import com.dto.response.FlightResponseDTO;
import com.dto.response.assignmentrequirements.PassengerInsertResponseDTO;
import com.dto.response.assignmentrequirements.UserDetailsResponseDTO;
import com.entity.Role;
import com.entity.User;
import com.entity.assignmentrequirements.Flight;
import com.entity.assignmentrequirements.Passenger;
import com.entity.assignmentrequirements.PassengerDetails;
import com.exception.EntityNotFoundException;
import com.repository.FlightRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.util.FlightDTOConvertor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class PassengerServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private FlightDTOConvertor dtoMock;
    @InjectMocks
    private PassengerServiceImpl passengerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("The testPassengerShouldInsert method should return")
    public void insertPassengerShouldReturn() throws Exception {
        String username = "Darius";
        User user = new User();
        user.setUserId(1L);

        user.setPassenger(new Passenger());
        user.getPassenger().setPassengerDetails(new PassengerDetails());

        PassengerInsertDTO passengerInsertDTO = new PassengerInsertDTO();
        passengerInsertDTO.setUsername(username);

        Role role = new Role(1L, "ROLE_User");
        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.getRoleByRoleName("ROLE_User")).thenReturn(role);


        PassengerInsertResponseDTO passengerInsertResponseDTO = passengerService.insertPassenger(passengerInsertDTO);
        assertEquals(username, passengerInsertResponseDTO.getUsername());


    }

    @Test
    @DisplayName("The testPassengerShouldInsert method should throw EntityNotFoundException")
    public void insertPassengerShouldThrowEntityNotFound() {
        PassengerInsertDTO passengerInsertDTO = new PassengerInsertDTO();
        passengerInsertDTO.setUsername("Darius");

        Mockito.when(roleRepository.getRoleByRoleName("ROLE_User")).thenReturn(null);

        assertThatThrownBy(() -> passengerService.insertPassenger(passengerInsertDTO))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("The getFlightsByCity1AndCity2 method should return")
    public void getFlightsByCity1AndCity2ShouldReturn() {
        String city1 = "city1";
        String city2 = "city2";
        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        flight.setFlightId(1L);
        flight.setCity1(city1);
        flight.setCity2(city2);
        flights.add(flight);

        FlightResponseDTO dto = new FlightResponseDTO(flight.getFlightId(), flight.getFlightName());


        Mockito.when(flightRepository.findByCity1AndCity2(city1, city2)).thenReturn(flights);
        List<FlightResponseDTO> flightResponseDTOS = passengerService.getFlightsByCity1AndCity2(city1, city2);
        //Mockito.when(dtoMock.flightToFlightResponseDTO(flight)).thenReturn(dto);
        //assertEquals(dto, flightResponseDTOS.get(0));
        assertEquals(1, flightResponseDTOS.size());
    }

    @Test
    @DisplayName("The getUserDetails method should throw EntityNotFoundException")
    public void getUserDetailsShouldReturn() throws EntityNotFoundException {
        String username = "Darius";
        User user = new User();
        user.setUserId(1L);
        user.setUsername(username);
        user.setPassenger(new Passenger());
        user.getPassenger().setPassengerDetails(new PassengerDetails());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDetailsResponseDTO userDetailsResponseDTO = passengerService.getUserDetails(1L);
        assertEquals(username, userDetailsResponseDTO.getUsername());
    }

    @Test
    @DisplayName("The testGetUserDetailsShouldThrowEntityNotFoundException method should throw EntityNotFoundException")
    public void getUserDetailsShouldThrow() {
        String username = "Darius";
        User user = new User();
        user.setUserId(1L);
        user.setUsername(username);
        user.setPassenger(new Passenger());
        user.getPassenger().setPassengerDetails(new PassengerDetails());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> passengerService.getUserDetails(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}