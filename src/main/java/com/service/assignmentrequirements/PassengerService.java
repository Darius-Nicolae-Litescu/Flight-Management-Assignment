package com.service.assignmentrequirements;

import com.dto.request.insert.assignmentrequirements.PassengerInsertDTO;
import com.dto.response.FlightResponseDTO;
import com.dto.response.assignmentrequirements.PassengerInsertResponseDTO;
import com.dto.response.assignmentrequirements.UserDetailsResponseDTO;
import com.exception.EntityNotFoundException;

import java.util.List;

public interface PassengerService {
    PassengerInsertResponseDTO insertPassenger(PassengerInsertDTO passengerInsertDTO) throws Exception;

    List<FlightResponseDTO> getFlightsByCity1AndCity2(String city1, String city2);

    UserDetailsResponseDTO getUserDetails(Long userId) throws EntityNotFoundException;
}
