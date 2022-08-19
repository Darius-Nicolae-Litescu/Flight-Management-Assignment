package com.service;

import com.dto.request.insert.SeatInsertDTO;
import com.dto.request.update.SeatUpdateDTO;
import com.dto.response.SeatResponseDTO;
import com.exception.EntityNotFoundException;

import java.util.List;

public interface SeatService {

	List<SeatResponseDTO> addSeat(Long flightId, SeatInsertDTO seatInsertDTO) throws EntityNotFoundException;

	SeatResponseDTO updateSeat(Long flightId, SeatUpdateDTO seatUpdateDTO) throws EntityNotFoundException;

	List<SeatResponseDTO> getAllSeats();

}
