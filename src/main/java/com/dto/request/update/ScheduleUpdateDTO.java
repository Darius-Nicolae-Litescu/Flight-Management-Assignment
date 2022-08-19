package com.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateDTO {
    @NotNull(message = "ID field is required")
    private Long id;
    @NotNull(message = "Departure time field is required")
    private LocalTime departureTime;
    @NotNull(message = "Landing time field is required")
    private LocalTime landingTime;
    @NotNull(message = "Stop field is required")
    private String stop;
}