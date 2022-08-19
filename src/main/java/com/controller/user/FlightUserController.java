package com.controller.user;

import com.dto.response.FlightResponseDTO;
import com.entity.assignmentrequirements.Flight;
import com.exception.EntityNotFoundException;
import com.service.FlightService;
import com.wrapper.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/abcflights/user")
public class FlightUserController {

	private final FlightService flightService;

	@Autowired
	public FlightUserController(FlightService flightService) {
		this.flightService = flightService;
	}


	/*
	@GetMapping("/flight/{flightId}")
	public Flight getFlightBasedOnId(@PathVariable Long flightId) {
		Optional<Flight> flightOptional = flightService.getFlightById(flightId);
		return flightOptional.orElse(null);
	}
	 */

	@GetMapping("/flight/{flightId}")
	public FlightResponseDTO getFlightBasedOnId(@PathVariable Long flightId) throws EntityNotFoundException {
		FlightResponseDTO flightResponseDto = flightService.getFlightById(flightId);
		return flightResponseDto;
	}


	@GetMapping("/flights")
	public GenericResponse<List<Flight>> getAllFlights(Authentication authentication) {
		List<String> authorities = authentication.getAuthorities()
				.stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority())
				.collect(Collectors.toList());
		return new GenericResponse<>(authorities, flightService.getAllFlight());
	}

	@GetMapping("/flights/name")
	public List<FlightResponseDTO> getFlightsByFlightName(@RequestParam(value = "flightName") String flightName) {
		return flightService.getFlightsByFlightName(flightName);
	}

	@GetMapping("/flights/city1")
	public List<FlightResponseDTO> getFlightsByCity1(@RequestParam(value = "city1") String city1) {
		return flightService.getFlightsByCity1(city1);
	}

	@GetMapping("/flights/city2")
	public List<FlightResponseDTO> getFlightsByCity2(@RequestParam(value = "city2") String city2) {
		return flightService.getFlightsByCity2(city2);
	}

	@GetMapping("/flights/flightType")
	public List<FlightResponseDTO> getFlightsByFlightType(@RequestParam(value = "flightType") String flightType) {
		return flightService.getFlightsByFlightType(flightType);
	}

	@GetMapping("/flights/all")
	public List<FlightResponseDTO> getFlightsByFlightNameAndCity1AndCity2AndFlightType(
			@RequestParam(value = "flightName") String flightName,
			@RequestParam(value = "city1") String city1,
			@RequestParam(value = "city2") String city2,
			@RequestParam(value = "flightType") String flightType) {
		return flightService.getFlightsByFlightNameAndCity1AndCity2AndFlightType(flightName, city1, city2, flightType);
	}

	@GetMapping("/flights/generic-error-message")
	public void getGenericErrorMessage() {
		Flight flight = null;
		flight.getSeats();
	}

}


