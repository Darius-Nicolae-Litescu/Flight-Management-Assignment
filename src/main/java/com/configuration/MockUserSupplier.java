package com.configuration;

import com.entity.Role;
import com.entity.User;
import com.entity.assignmentrequirements.Passenger;
import com.entity.assignmentrequirements.PassengerDetails;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MockUserSupplier {
    private final String[] usernames = {"user", "admin", "DariusNicolae"};
    private final String[] firstNames = {"Darius", "Litescu", "Nicolae"};
    private final String[] lastNames = {"Litescu", "Darius", "Nicolae"};

    private final String[][] roles = {{"ROLE_User"}, {"ROLE_Admin"}, {"ROLE_Admin"}};
    private final String password = "1234";

    private final Random random = new Random();
    public final static MockUserSupplier supplier = new MockUserSupplier();

    private MockUserSupplier() {
    }

    private Integer userCount = 0;

    Supplier<Set<Role>> rolesSupplier = () -> {
        Set<Role> allRoles = Arrays.asList(roles)
                .stream()
                .flatMap(roles -> Arrays.stream(roles).map(role-> new Role(null, role))
                        .collect(Collectors.toList())
                        .stream())
                .collect(Collectors.toSet());
        return allRoles;
    };
    Function<Role, User> userSupplier = (Role roleToAdd) -> {
        User customUser = new User();
        if (userCount < 3) {
            String username = usernames[userCount];
            String[] userRoles = roles[userCount];
            String userPassword = password;
            customUser.setUsername(username);
            customUser.setRoles(Arrays.asList(roleToAdd));
            customUser.setPassenger(new Passenger());
            customUser.getPassenger().setPassengerDetails(new PassengerDetails());
            customUser.getPassenger().getPassengerDetails().setFirstName(firstNames[userCount]);
            customUser.getPassenger().getPassengerDetails().setLastName(lastNames[userCount]);
            customUser.getPassenger().getPassengerDetails().setPhoneNumber(String.valueOf(Math.abs(random.nextLong())));
            customUser.getPassenger().getPassengerDetails().setGender("MALE");
            customUser.getPassenger().getPassengerDetails().setAge(21);
            customUser.setPassword(userPassword);
            userCount++;
        } else {
            customUser.setUsername("Duplicate" + Math.abs(new Random().nextLong()));
            customUser.setPassword(password);
            customUser.setRoles(Arrays.asList(roleToAdd));
            customUser.setPassenger(new Passenger());
            customUser.getPassenger().setPassengerDetails(new PassengerDetails());
            customUser.getPassenger().getPassengerDetails().setFirstName(generateRandomString(10));
            customUser.getPassenger().getPassengerDetails().setLastName(generateRandomString(10));
            customUser.getPassenger().getPassengerDetails().setPhoneNumber(String.valueOf(Math.abs(random.nextLong())));
            customUser.getPassenger().getPassengerDetails().setGender("MALE");
            customUser.getPassenger().getPassengerDetails().setAge(Math.abs(random.nextInt())%100);
        }
        return customUser;
    };

    public final String generateRandomString(int numberOfChars){
        String alphabet = "abcdefghijklmnopqrtuvyz";
        StringBuilder resultingString = new StringBuilder();
        resultingString.append(Character.toUpperCase(alphabet.charAt(random.nextInt(alphabet.length()))));
        for (int i = 0; i < numberOfChars; i++) {
            resultingString.append(Character.toLowerCase(alphabet.charAt(random.nextInt(alphabet.length()))));
        }
        return resultingString.toString();
    }
}
