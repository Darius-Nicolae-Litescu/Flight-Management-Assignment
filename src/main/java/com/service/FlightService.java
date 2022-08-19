package com.service;

import com.dto.request.insert.FlightInsertDTO;
import com.dto.request.update.FlightUpdateDTO;
import com.dto.response.FlightResponseDTO;
import com.entity.assignmentrequirements.Flight;
import com.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface FlightService {

	List<FlightResponseDTO> getFlightsByFlightName(String flightName);

	List<FlightResponseDTO> getFlightsByCity1(String city1);

	List<FlightResponseDTO> getFlightsByCity2(String city2);

	List<FlightResponseDTO> getFlightsByFlightType(String flightType);

	List<FlightResponseDTO> getFlightsByFlightNameAndCity1AndCity2AndFlightType(String flightName, String city1, String city2, String flightType);

	FlightResponseDTO addFlight(FlightInsertDTO flightInsertDTO);

	FlightResponseDTO updateFlight(FlightUpdateDTO flightUpdateDTO) throws EntityNotFoundException;

	List<Flight> getAllFlight();

	FlightResponseDTO getFlightById(Long flightId) throws EntityNotFoundException;

	Optional<Flight> getFlightModelById(Long flightId);
}
