package com.service;

import com.dto.request.insert.SeatInsertDTO;
import com.dto.request.update.SeatUpdateDTO;
import com.dto.response.SeatResponseDTO;
import com.entity.assignmentrequirements.Flight;
import com.entity.Seat;
import com.exception.EntityNotFoundException;
import com.repository.FlightRepository;
import com.repository.SeatRepository;
import com.util.SeatDTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

	private final FlightRepository flightRepository;

	private final SeatRepository seatRepository;

	private SeatDTOConvertor seatDTOConvertor;

	@Autowired
	public SeatServiceImpl(FlightRepository flightRepository, SeatRepository seatRepository) {
		this.flightRepository = flightRepository;
		this.seatRepository = seatRepository;
	}

	@Autowired
	public void setSeatDTOConvertor(SeatDTOConvertor seatDTOConvertor) {
		this.seatDTOConvertor = seatDTOConvertor;
	}

	@Override
	public List<SeatResponseDTO> addSeat(Long flightId, SeatInsertDTO seatInsertDTO) throws EntityNotFoundException {
		Optional<Flight> flightOptional = flightRepository.findById(flightId);
		if (flightOptional.isPresent()) {
			Flight flight = flightOptional.get();
			Seat seat = seatDTOConvertor.seatInsertDTOToSeat(seatInsertDTO);
			flight.getSeats().add(seat);
			flightRepository.save(flight);

			List<SeatResponseDTO> seatResponseDTOs = seatDTOConvertor.convertAllSeatsToSeatResponseDTOs(flight.getSeats(), flightId);
			return seatResponseDTOs;
		} else {
			throw new EntityNotFoundException(flightId);
		}
	}

	@Override
	public SeatResponseDTO updateSeat(Long flightId, SeatUpdateDTO seatUpdateDTO) throws EntityNotFoundException {
		Optional<Seat> seat = seatRepository.findById(seatUpdateDTO.getId());
		if (seat.isPresent()) {
			Seat currentSeat = seatDTOConvertor.seatUpdateDTOToSeat(seatUpdateDTO);
			seatRepository.save(currentSeat);
			SeatResponseDTO seatResponseDTO = seatDTOConvertor.convertSeatToSeatResponseDTO(currentSeat, flightId);
			return seatResponseDTO;
		} else {
			throw new EntityNotFoundException(seatUpdateDTO.getId());
		}
	}

	@Override
	public List<SeatResponseDTO> getAllSeats() {
		List<SeatResponseDTO> seatResponseDTOS = seatRepository.findAll()
				.stream()
				.map(seat -> seatDTOConvertor.convertSeatToSeatResponseDTO(seat, null))
				.collect(Collectors.toList());
		return seatResponseDTOS;
	}


}
