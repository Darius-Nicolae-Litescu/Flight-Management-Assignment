package com.controller.user.assignmentrequirements;

import com.controller.user.FlightUserController;
import com.dto.request.insert.assignmentrequirements.PassengerInsertDTO;
import com.dto.response.FlightResponseDTO;
import com.dto.response.assignmentrequirements.PassengerInsertResponseDTO;
import com.dto.response.assignmentrequirements.UserDetailsResponseDTO;
import com.exception.EntityNotFoundException;
import com.service.FlightService;
import com.service.assignmentrequirements.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/abcflights/passenger")
public class FlightPassengerRequirementsController {
    Logger logger = LoggerFactory.getLogger(FlightPassengerRequirementsController.class);
    private final FlightService flightService;
    private final PassengerService passengerService;

    @Autowired
    public FlightPassengerRequirementsController(FlightService flightService, PassengerService passengerService) {
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerInsertResponseDTO> addPassenger(@RequestBody PassengerInsertDTO passengerInsertDTO) throws Exception {
        logger.info("Adding passenger: {}", passengerInsertDTO);

        PassengerInsertResponseDTO passengerInsertResponseDTO = passengerService.insertPassenger(passengerInsertDTO);
        if (passengerInsertResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(passengerInsertResponseDTO, HttpStatus.CREATED);
    }


    @GetMapping("/flights")
    public List<FlightResponseDTO> getFlightsByCity1AndCity2(
            @RequestParam(value = "city1") String city1,
            @RequestParam(value = "city2") String city2) throws EntityNotFoundException {
        logger.info("Getting flights by city1: {} and city2: {}", city1, city2);

        List<FlightResponseDTO> flightResponseDTOS = passengerService.getFlightsByCity1AndCity2(city1, city2);
        for (FlightResponseDTO flightResponseDTO : flightResponseDTOS) {
            Link link = linkTo(methodOn(FlightUserController.class)
                    .getFlightBasedOnId(flightResponseDTO.getFlightNumber()))
                    .withSelfRel();
            flightResponseDTO.add(link);
        }
        return flightResponseDTOS;
    }

    @GetMapping("/details/{userId}")
    public UserDetailsResponseDTO getUserDetails(@PathVariable Long userId) throws EntityNotFoundException {
        logger.info("Getting user details for userId: {}", userId);

        return passengerService.getUserDetails(userId);
    }
}
