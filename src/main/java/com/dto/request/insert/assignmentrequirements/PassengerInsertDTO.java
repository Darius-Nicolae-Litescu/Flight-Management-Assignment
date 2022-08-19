package com.dto.request.insert.assignmentrequirements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerInsertDTO {
    private String username;
    private String password;
    private Integer age;
    private String firstName;
    private String gender;
    private String phoneNumber;
}
