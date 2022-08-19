package com.controller.admin;

import com.dto.request.insert.SeatInsertDTO;
import com.dto.request.update.SeatUpdateDTO;
import com.dto.response.SeatResponseDTO;
import com.exception.EntityNotFoundException;
import com.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/abcflights/admin")
public class SeatAdminController {

    private final SeatService seatService;

    @Autowired
    public SeatAdminController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/{flightId}/seat")
    public ResponseEntity<List<SeatResponseDTO>> addSeat(@RequestBody @Valid SeatInsertDTO seatInsertDTO, @PathVariable Long flightId) throws EntityNotFoundException {
        List<SeatResponseDTO> seatResponseDTOS = seatService.addSeat(flightId, seatInsertDTO);
        if (seatResponseDTOS == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(seatResponseDTOS, HttpStatus.CREATED);
    }

    @PutMapping("/{flightId}/seat")
    public ResponseEntity<SeatResponseDTO> updateSeat(@RequestBody @Valid SeatUpdateDTO seatUpdateDTO, @PathVariable Long flightId) throws EntityNotFoundException {
        SeatResponseDTO seatResponseDTO = seatService.updateSeat(flightId, seatUpdateDTO);
        if (seatResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(seatResponseDTO, HttpStatus.CREATED);
    }


}


