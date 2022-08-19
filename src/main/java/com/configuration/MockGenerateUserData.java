package com.configuration;

import com.entity.Role;
import com.entity.User;
import com.exception.EntityNotFoundException;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class MockGenerateUserData {

    @Autowired
    private MockGenerateFlightData mockGenerateFlightData;
    private UserService userService;
    private final static int NUMBER_OF_USERS_TO_GENERATE = 50;
    private final MockUserSupplier supplier = MockUserSupplier.supplier;
    private List<User> customUserList;

    public MockGenerateUserData() {
        customUserList = new ArrayList<>();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
        initializeUsers();
    }

    private void initializeUsers() {
        Set<Role> roles = supplier.rolesSupplier.get();
        if (userService.roleCount() < 2) {
            roles = userService.addRoles(roles);
            if (userService.userCount() < 20) {
                for (int numberOfEntriesInserted = 0; numberOfEntriesInserted < NUMBER_OF_USERS_TO_GENERATE; numberOfEntriesInserted++) {
                    Role role = null;
                    if (numberOfEntriesInserted < 3) {
                        role = roles.stream().filter(roleToFilter -> roleToFilter.getRoleName().equals("ROLE_Admin")).findFirst().get();
                    } else {
                        role = roles.stream().filter(roleToFilter -> roleToFilter.getRoleName().equals("ROLE_User")).findFirst().get();
                    }
                    this.customUserList.add(addUser(supplier.userSupplier.apply(role)));
                }
                this.customUserList.stream().forEach(user -> {
                    User user2 = userService.insertUser(user);
                    try {
                        mockGenerateFlightData.addFlight(user2);
                    } catch (EntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                //userService.insertAll(this.customUserList);
            }
        }
    }

    public User addUser(User customUser) {
        this.customUserList.add(customUser);
        return customUser;
    }

    public UserDetails getUserByUsername(String username) {
        Optional<UserDetails> optionalUser = this.customUserList.stream().filter(customUser -> customUser.getUsername().equals(username))
                .map(user -> (UserDetails) user).findAny();
        return optionalUser.orElse(null);
    }

    public List<User> getAll() {
        return this.customUserList;
    }
}
