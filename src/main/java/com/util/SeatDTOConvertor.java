package com.util;

import com.dto.request.insert.SeatInsertDTO;
import com.dto.request.update.SeatUpdateDTO;
import com.dto.response.SeatResponseDTO;
import com.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeatDTOConvertor {

    public Seat seatUpdateDTOToSeat(SeatUpdateDTO seatUpdateDTO) {
        Seat seat = new Seat();
        seat.setSeatId(seatUpdateDTO.getId());
        seat.setPassengerName(seatUpdateDTO.getPassengerName());
        seat.setGender(seatUpdateDTO.getGender());
        seat.setAge(seatUpdateDTO.getAge());
        seat.setMobileNumber(seatUpdateDTO.getMobileNumber());
        return seat;
    }

    public Seat seatInsertDTOToSeat(SeatInsertDTO seatInsertDTO) {
        Seat seat = new Seat();
        seat.setPassengerName(seatInsertDTO.getPassengerName());
        seat.setGender(seatInsertDTO.getGender());
        seat.setAge(seatInsertDTO.getAge());
        seat.setMobileNumber(seatInsertDTO.getMobileNumber());
        return seat;
    }

    public SeatResponseDTO convertSeatToSeatResponseDTO(Seat seat, Long flightId) {
        SeatResponseDTO seatResponseDTO = new SeatResponseDTO();
        seatResponseDTO.setAge(seat.getAge());
        seatResponseDTO.setGender(seat.getGender());
        seatResponseDTO.setMobileNumber(seat.getMobileNumber());
        seatResponseDTO.setPassengerName(seat.getPassengerName());
        seatResponseDTO.setFlightId(flightId);
        return seatResponseDTO;
    }

    public List<SeatResponseDTO> convertAllSeatsToSeatResponseDTOs(List<Seat> seatList, Long flightId) {
        return seatList.stream().map(seat -> convertSeatToSeatResponseDTO(seat, flightId)).collect(Collectors.toList());
    }
}
