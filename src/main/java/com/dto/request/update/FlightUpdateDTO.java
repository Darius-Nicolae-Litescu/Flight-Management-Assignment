package com.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightUpdateDTO {
    @NotNull(message = "ID field is required")
    private Long id;
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