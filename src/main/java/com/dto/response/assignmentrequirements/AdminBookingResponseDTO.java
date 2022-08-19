package com.dto.response.assignmentrequirements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBookingResponseDTO {
    private String fullName;
    private String flightFromTo;
    private Date dateOfBooking;
}
