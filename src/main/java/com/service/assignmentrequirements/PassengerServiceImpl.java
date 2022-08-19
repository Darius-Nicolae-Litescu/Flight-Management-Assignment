package com.service.assignmentrequirements;

import com.dto.request.insert.assignmentrequirements.PassengerInsertDTO;
import com.dto.response.FlightResponseDTO;
import com.dto.response.assignmentrequirements.PassengerInsertResponseDTO;
import com.dto.response.assignmentrequirements.UserDetailsResponseDTO;
import com.entity.Role;
import com.entity.User;
import com.entity.assignmentrequirements.Booking;
import com.entity.assignmentrequirements.Flight;
import com.exception.EntityNotFoundException;
import com.repository.FlightRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.util.FlightDTOConvertor;
import com.util.assignmentrequirements.PassangerDTOConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public PassengerServiceImpl(FlightRepository flightRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public PassengerInsertResponseDTO insertPassenger(PassengerInsertDTO passengerInsertDTO) throws Exception {
        logger.info("Inserting user with details: {}", passengerInsertDTO);
        User user = PassangerDTOConvertor.convertPassengerInsertDtoToUser(passengerInsertDTO);
        Role role = roleRepository.getRoleByRoleName("ROLE_User");
        if(role == null){
            throw new Exception("Could not assign default role to Passenger account");
        }
        user.setRoles(new ArrayList<>());
        user.getRoles().add(role);
        userRepository.save(user);
        logger.info("Inserted user sucesfully: {}", user);

        PassengerInsertResponseDTO passengerInsertResponseDTO = PassangerDTOConvertor.convertUserToPassengerInsertResponseDTO(user);
        return passengerInsertResponseDTO;
    }

    @Override
    public List<FlightResponseDTO> getFlightsByCity1AndCity2(String city1, String city2) {
        logger.info("Retrieving flights by city1: {}, city2: {}", city1, city2);

        List<Flight> flightList = flightRepository.findByCity1AndCity2(city1, city2);
        List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
                .map(flight -> FlightDTOConvertor.flightToFlightResponseDTO(flight))
                .collect(Collectors.toList());
        logger.info("Retrieving flights successfully city1: {}, city2: {}", city1, city2);
        return flightResponseDTOS;
    }

    @Override
    public UserDetailsResponseDTO getUserDetails(Long userId) throws EntityNotFoundException {
        logger.info("Retrieving user with id: {}", userId);

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(userId);
        }
        List<Booking> bookingList = userOptional.get().getPassenger().getAllBookings();

        UserDetailsResponseDTO userDetailsResponseDTO = PassangerDTOConvertor.convertUserToUserDetailsResponseDto(userOptional.get(), bookingList);
        logger.info("Retrieved user details sucesfully: {}", userOptional.get());

        return userDetailsResponseDTO;

    }


}
