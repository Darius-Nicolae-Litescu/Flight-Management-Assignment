package com.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class SeatUpdateDTO {
    @NotNull(message = "ID field is required")
    private Long id;
    @NotNull(message = "Passenger name field is required")
    private String passengerName;
    @NotNull(message = "Gender field is required")
    private String gender;
    @NotNull(message = "Passenger age field is required")
    private Integer age;
    @NotNull(message = "Mobile phone number field is required")
    private Long mobileNumber;
}