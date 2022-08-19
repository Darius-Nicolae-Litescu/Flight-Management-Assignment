package com.dto.request.insert.assignmentrequirements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBookingInsertDTO {
    private Long userId;
    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
}
