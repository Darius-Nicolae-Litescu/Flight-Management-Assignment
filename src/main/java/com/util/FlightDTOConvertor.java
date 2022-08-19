package com.util;

import com.dto.request.insert.FlightInsertDTO;
import com.dto.request.update.FlightUpdateDTO;
import com.dto.response.FlightResponseDTO;
import com.entity.assignmentrequirements.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightDTOConvertor {

	public Flight flightUpdateDTOToFlight(FlightUpdateDTO flightUpdateDTO) {
		Flight flight = new Flight();
		flight.setFlightId(flightUpdateDTO.getId());
		flight.setFlightName(flightUpdateDTO.getFlightName());
		flight.setCity1(flightUpdateDTO.getCity1());
		flight.setCity2(flightUpdateDTO.getCity2());
		flight.setFlightType(flightUpdateDTO.getFlightType());
		return flight;
	}

	public Flight flightInsertDTOToFlight(FlightInsertDTO flightInsertDTO) {
		Flight flight = new Flight();
		flight.setFlightName(flightInsertDTO.getFlightName());
		flight.setCity1(flightInsertDTO.getCity1());
		flight.setCity2(flightInsertDTO.getCity2());
		flight.setFlightType(flightInsertDTO.getFlightType());
		return flight;
	}

	public static FlightResponseDTO flightToFlightResponseDTO(Flight flight) {
		FlightResponseDTO flightResponseDTO = new FlightResponseDTO();
		flightResponseDTO.setFlightName(flight.getFlightName());
		flightResponseDTO.setFlightNumber(flight.getFlightId());
		return flightResponseDTO;
	}

}
