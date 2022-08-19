package com.util;

import com.dto.request.insert.ScheduleInsertDTO;
import com.dto.request.update.ScheduleUpdateDTO;
import com.dto.response.ScheduleResponseDTO;
import com.entity.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDTOConvertor {

    public Schedule scheduleUpdateDTOToSchedule(ScheduleUpdateDTO scheduleUpdateDTO) {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(scheduleUpdateDTO.getId());
        schedule.setDepartureTime(scheduleUpdateDTO.getDepartureTime());
        schedule.setLandingTime(scheduleUpdateDTO.getLandingTime());
        schedule.setStop(scheduleUpdateDTO.getStop());
        return schedule;
    }

    public Schedule scheduleInsertDTOToSchedule(ScheduleInsertDTO scheduleInsertDTO) {
        Schedule schedule = new Schedule();
        schedule.setDepartureTime(scheduleInsertDTO.getDepartureTime());
        schedule.setLandingTime(scheduleInsertDTO.getLandingTime());
        schedule.setStop(scheduleInsertDTO.getStop());
        return schedule;
    }

    public ScheduleResponseDTO convertScheduleToScheduleResponseDTO(Schedule schedule, Long flightId) {
        ScheduleResponseDTO scheduleResponseDTO = new ScheduleResponseDTO();
        scheduleResponseDTO.setDepartureTime(schedule.getDepartureTime());
        scheduleResponseDTO.setLandingTime(schedule.getLandingTime());
        scheduleResponseDTO.setStop(schedule.getStop());
        scheduleResponseDTO.setFlightId(flightId);
        return scheduleResponseDTO;
    }
}
