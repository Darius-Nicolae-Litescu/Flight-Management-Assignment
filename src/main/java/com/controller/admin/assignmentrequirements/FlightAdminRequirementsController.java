package com.controller.admin.assignmentrequirements;

import com.dto.request.insert.assignmentrequirements.AdminBookingInsertDTO;
import com.dto.response.assignmentrequirements.AdminBookingResponseDTO;
import com.exception.EntityNotFoundException;
import com.service.assignmentrequirements.BookingService;
import com.service.FlightService;
import com.wrapper.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abcflights/admin/passengers")
public class FlightAdminRequirementsController {
    Logger logger = LoggerFactory.getLogger(FlightAdminRequirementsController.class);

    private final FlightService flightService;
    private final BookingService bookingService;

    @Autowired
    public FlightAdminRequirementsController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @PostMapping("/{flightId}")
    public ResponseEntity<AdminBookingResponseDTO> addPassengerToFlight(@RequestBody AdminBookingInsertDTO adminBookingInsertDTO,
                                                                                  @PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Adding passenger to flight");
        AdminBookingResponseDTO adminBookingResponseDTO = bookingService.addPassangerToFlight(adminBookingInsertDTO, flightId);
        if (adminBookingResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ListWrapper<List<AdminBookingResponseDTO>>> getFlightPassengerBookingList(@PathVariable Long userId) throws EntityNotFoundException {
        logger.info("Getting flight passenger booking list");

        ListWrapper<List<AdminBookingResponseDTO>> adminBookingResponseDTOListWrapper =
                bookingService.getFlightPassengerBookingList(userId);
        if (adminBookingResponseDTOListWrapper.getList() == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTOListWrapper, HttpStatus.CREATED);

    }
}
