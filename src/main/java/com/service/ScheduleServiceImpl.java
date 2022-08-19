package com.service;

import com.dto.request.insert.ScheduleInsertDTO;
import com.dto.request.update.ScheduleUpdateDTO;
import com.dto.response.ScheduleResponseDTO;
import com.entity.assignmentrequirements.Flight;
import com.entity.Schedule;
import com.exception.EntityNotFoundException;
import com.repository.FlightRepository;
import com.repository.ScheduleRepository;
import com.util.ScheduleDTOConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private final FlightRepository flightRepository;

	private final ScheduleRepository scheduleRepository;
	private ScheduleDTOConvertor scheduleDTOConvertor;

	@Autowired
	public ScheduleServiceImpl(FlightRepository flightRepository, ScheduleRepository scheduleRepository) {
		this.flightRepository = flightRepository;
		this.scheduleRepository = scheduleRepository;
	}

	@Autowired
	public void setScheduleDTOConvertor(ScheduleDTOConvertor scheduleDTOConvertor) {
		this.scheduleDTOConvertor = scheduleDTOConvertor;
	}

	@Override
	public ScheduleResponseDTO addSchedule(Long flightId, ScheduleInsertDTO scheduleInsertDTO) {
		Optional<Flight> flightOptional = flightRepository.findById(flightId);
		if (flightOptional.isPresent()) {
			Flight flight = flightOptional.get();
			Schedule schedule = scheduleDTOConvertor.scheduleInsertDTOToSchedule(scheduleInsertDTO);
			flight.setFlightSchedule(schedule);
			flightRepository.save(flight);
			ScheduleResponseDTO scheduleResponseDTO = scheduleDTOConvertor
					.convertScheduleToScheduleResponseDTO(flight.getFlightSchedule(), flightId);
			return scheduleResponseDTO;
		}
		return null;
	}

	@Override
	public ScheduleResponseDTO updateSchedule(Long flightId, ScheduleUpdateDTO scheduleUpdateDTO) throws EntityNotFoundException {
		Optional<Flight> flightOptional = flightRepository.findById(flightId);
		if (flightOptional.isPresent()) {
			Flight flight = flightOptional.get();
			Schedule schedule = scheduleDTOConvertor.scheduleUpdateDTOToSchedule(scheduleUpdateDTO);
			Schedule scheduleToUpdate = flight.getFlightSchedule();
			scheduleToUpdate.setDepartureTime(schedule.getDepartureTime());
			scheduleToUpdate.setLandingTime(schedule.getLandingTime());
			scheduleToUpdate.setStop(schedule.getStop());

			flightRepository.save(flight);

			ScheduleResponseDTO scheduleResponseDTO = scheduleDTOConvertor.convertScheduleToScheduleResponseDTO(scheduleToUpdate, flightId);
			return scheduleResponseDTO;
		} else {
			throw new EntityNotFoundException(scheduleUpdateDTO.getId());
		}
	}

	@Override
	public List<ScheduleResponseDTO> getAllSchedules() {
		List<ScheduleResponseDTO> scheduleResponseDTOS = scheduleRepository.findAll()
				.stream()
				.map(schedule -> scheduleDTOConvertor.convertScheduleToScheduleResponseDTO(schedule, null))
				.collect(Collectors.toList());
		return scheduleResponseDTOS;
	}


}
