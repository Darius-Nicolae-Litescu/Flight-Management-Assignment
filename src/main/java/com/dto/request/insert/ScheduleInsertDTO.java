package com.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ScheduleInsertDTO {
    @NotNull(message = "Departure time field is required")
    private LocalTime departureTime;
    @NotNull(message = "Landing time field is required")
    private LocalTime landingTime;
    @NotNull(message = "Stop field is required")
    private String stop;
}