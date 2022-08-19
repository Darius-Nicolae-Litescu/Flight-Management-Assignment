package com.util.assignmentrequirements;

import com.controller.user.assignmentrequirements.FlightPassengerRequirementsController;
import com.dto.request.insert.assignmentrequirements.PassengerInsertDTO;
import com.dto.response.assignmentrequirements.PassengerInsertResponseDTO;
import com.dto.response.assignmentrequirements.UserDetailsResponseDTO;
import com.entity.User;
import com.entity.assignmentrequirements.Booking;
import com.entity.assignmentrequirements.Passenger;
import com.entity.assignmentrequirements.PassengerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PassangerDTOConvertor {
    Logger logger = LoggerFactory.getLogger(PassangerDTOConvertor.class);


    public static UserDetailsResponseDTO convertUserToUserDetailsResponseDto(User user, List<Booking> bookingList) {
        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO();
        userDetailsResponseDTO.setUsername(user.getUsername());
        if (user.getPassenger() != null && user.getPassenger().getPassengerDetails() != null) {
            userDetailsResponseDTO.setAge(user.getPassenger().getPassengerDetails().getAge());
            userDetailsResponseDTO.setGender(user.getPassenger().getPassengerDetails().getGender());
            userDetailsResponseDTO.setPhoneNumber(user.getPassenger().getPassengerDetails().getPhoneNumber());
            userDetailsResponseDTO.setFirstName(user.getPassenger().getPassengerDetails().getFirstName());
        }
        for (Booking booking : bookingList) {
            userDetailsResponseDTO.addBookingResponse(booking.getBookingId(), booking.getDateOfBooking());
        }
        return userDetailsResponseDTO;
    }

    public static User convertPassengerInsertDtoToUser(PassengerInsertDTO passengerInsertDTO) {
        User user = new User();
        user.setPassenger(new Passenger());
        user.getPassenger().setPassengerDetails(new PassengerDetails());
        user.setUsername(passengerInsertDTO.getUsername());
        user.setPassword(passengerInsertDTO.getPassword());

        user.getPassenger().getPassengerDetails().setAge(passengerInsertDTO.getAge());
        user.getPassenger().getPassengerDetails().setGender(passengerInsertDTO.getGender());
        user.getPassenger().getPassengerDetails().setFirstName(passengerInsertDTO.getFirstName());
        user.getPassenger().getPassengerDetails().setLastName(passengerInsertDTO.getFirstName());
        user.getPassenger().getPassengerDetails().setPhoneNumber(passengerInsertDTO.getPhoneNumber());
        return user;
    }

    public static PassengerInsertResponseDTO convertUserToPassengerInsertResponseDTO(User user) {
        PassengerInsertResponseDTO passengerInsertResponseDTO = new PassengerInsertResponseDTO();
        passengerInsertResponseDTO.setUsername(user.getUsername());
        if (user.getPassenger() != null) {
            passengerInsertResponseDTO.setFirstName(user.getPassenger().getPassengerDetails().getFirstName());
            passengerInsertResponseDTO.setLastName(user.getPassenger().getPassengerDetails().getLastName());
        }
        return passengerInsertResponseDTO;
    }
}
