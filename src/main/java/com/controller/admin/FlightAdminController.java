package com.controller.admin;

import com.dto.request.insert.FlightInsertDTO;
import com.dto.request.update.FlightUpdateDTO;
import com.dto.response.FlightResponseDTO;
import com.exception.EntityNotFoundException;
import com.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/abcflights/admin")
public class FlightAdminController {

    private final FlightService flightService;

    @Autowired
    public FlightAdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/flight")
    public ResponseEntity<FlightResponseDTO> saveFlight(@RequestBody @Valid FlightInsertDTO flightInsertDTO) {
        FlightResponseDTO flightResponseDTO = flightService.addFlight(flightInsertDTO);
        if (flightResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(flightResponseDTO, HttpStatus.CREATED);
    }


    @PutMapping("/flight")
    public ResponseEntity<FlightResponseDTO> updateFlight(@RequestBody @Valid FlightUpdateDTO flightUpdateDTO) throws EntityNotFoundException {
        FlightResponseDTO flightResponseDTO = flightService.updateFlight(flightUpdateDTO);
        if (flightResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(flightResponseDTO, HttpStatus.CREATED);
    }

}


