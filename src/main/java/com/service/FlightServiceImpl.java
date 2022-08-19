package com.service;

import com.dto.request.insert.FlightInsertDTO;
import com.dto.request.update.FlightUpdateDTO;
import com.dto.response.FlightResponseDTO;
import com.entity.assignmentrequirements.Flight;
import com.exception.EntityNotFoundException;
import com.repository.FlightRepository;
import com.util.FlightDTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

	private final FlightRepository flightRepository;
	private FlightDTOConvertor flightDTOConvertor;

	@Autowired
	public FlightServiceImpl(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Autowired
	public void setFlightDTOConvertor(FlightDTOConvertor flightDTOConvertor) {
		this.flightDTOConvertor = flightDTOConvertor;
	}


	@Override
	public List<FlightResponseDTO> getFlightsByFlightName(String flightName) {
		List<Flight> flightList = flightRepository.findByFlightName(flightName);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> flightDTOConvertor.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByCity1(String city1) {
		List<Flight> flightList = flightRepository.findByCity1(city1);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> flightDTOConvertor.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByCity2(String city2) {
		List<Flight> flightList = flightRepository.findByCity2(city2);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> flightDTOConvertor.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByFlightType(String flightType) {
		List<Flight> flightList = flightRepository.findByFlightType(flightType);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> flightDTOConvertor.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByFlightNameAndCity1AndCity2AndFlightType(String flightName, String city1, String city2, String flightType) {
		List<Flight> flightList = flightRepository.findByFlightNameAndCity1AndCity2AndFlightType(flightName, city1, city2, flightType);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> flightDTOConvertor.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public FlightResponseDTO addFlight(FlightInsertDTO flightInsertDTO) {
		Flight flight = flightDTOConvertor.flightInsertDTOToFlight(flightInsertDTO);
		Flight savedFlight = flightRepository.save(flight);
		return flightDTOConvertor.flightToFlightResponseDTO(savedFlight);
	}

	@Override
	public List<Flight> getAllFlight() {

		return flightRepository.findAll();
	}

	@Override
	public FlightResponseDTO updateFlight(FlightUpdateDTO flightUpdateDTO) throws EntityNotFoundException {
		Optional<Flight> flightFromDatabase = flightRepository.findById(flightUpdateDTO.getId());
		if (flightFromDatabase.isPresent()) {
			Flight flight = flightFromDatabase.get();
			flight.setFlightName(flightUpdateDTO.getFlightName());
			flight.setFlightType(flightUpdateDTO.getFlightType());
			flight.setCity1(flightUpdateDTO.getCity1());
			flight.setCity2(flightUpdateDTO.getCity2());
			flightRepository.save(flight);
			return flightDTOConvertor.flightToFlightResponseDTO(flight);
		} else {
			throw new EntityNotFoundException(flightUpdateDTO.getId());
		}
	}

	@Override
	public Optional<Flight> getFlightModelById(Long flightId) {
		return flightRepository.findById(flightId);
	}

	@Override
	public FlightResponseDTO getFlightById(Long flightId) throws EntityNotFoundException {
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (!flight.isPresent()) {
			throw new EntityNotFoundException(flightId);
		}
		FlightResponseDTO flightResponseDTO = new FlightResponseDTO();
		flightResponseDTO.setFlightNumber(flightId);
		flightResponseDTO.setFlightName(flight.get().getFlightName());
		return flightResponseDTO;
	}

}
