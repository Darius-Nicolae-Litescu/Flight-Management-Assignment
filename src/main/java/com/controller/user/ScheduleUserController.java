package com.controller.user;

import com.dto.request.insert.ScheduleInsertDTO;
import com.dto.request.update.ScheduleUpdateDTO;
import com.dto.response.ScheduleResponseDTO;
import com.exception.EntityNotFoundException;
import com.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/abcflights/user")
public class ScheduleUserController {

	private final ScheduleService scheduleService;

	@Autowired
	public ScheduleUserController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping("/{flightId}/schedule")
	public ResponseEntity<ScheduleResponseDTO> addSchedule(@RequestBody @Valid ScheduleInsertDTO scheduleInsertDTO, @PathVariable Long flightId) {
		ScheduleResponseDTO scheduleResponseDTO = scheduleService.addSchedule(flightId, scheduleInsertDTO);
		if (scheduleResponseDTO == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(scheduleResponseDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{flightId}/schedule")
	public ResponseEntity<ScheduleResponseDTO> updateSchedule(@RequestBody @Valid ScheduleUpdateDTO scheduleUpdateDTO, @PathVariable Long flightId) throws EntityNotFoundException {
		ScheduleResponseDTO scheduleResponseDTO = scheduleService.updateSchedule(flightId, scheduleUpdateDTO);
		if (scheduleResponseDTO == null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(scheduleResponseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/schedule/all")
	public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules(){
		return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
	}

}


