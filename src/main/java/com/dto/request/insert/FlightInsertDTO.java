package com.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class FlightInsertDTO {
    @NotNull(message = "Passenger name field is required")
    @Size(min = 1, max = 150)
    private String flightName;
    @NotNull(message = "From city field is required")
    @Size(min = 1, max = 150)
    private String city1;
    @NotNull(message = "To city field is required")
    @Size(min = 1, max = 150)
    private String city2;
    @NotNull(message = "Flight type field is required")
    @Size(min = 1, max = 150)
    private String flightType;
}