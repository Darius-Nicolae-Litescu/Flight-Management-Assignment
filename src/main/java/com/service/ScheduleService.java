package com.service;

import com.dto.request.insert.ScheduleInsertDTO;
import com.dto.request.update.ScheduleUpdateDTO;
import com.dto.response.ScheduleResponseDTO;
import com.exception.EntityNotFoundException;

import java.util.List;

public interface ScheduleService {

	ScheduleResponseDTO addSchedule(Long flightId, ScheduleInsertDTO scheduleInsertDTO);

	ScheduleResponseDTO updateSchedule(Long flightId, ScheduleUpdateDTO scheduleUpdateDTO) throws EntityNotFoundException;

	List<ScheduleResponseDTO> getAllSchedules();

}
