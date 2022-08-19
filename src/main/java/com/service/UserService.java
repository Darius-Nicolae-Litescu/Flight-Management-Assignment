package com.service;

import com.entity.Role;
import com.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Long roleCount();

    User insertUser(User user);

     Long userCount();

    void insertAll(List<User> usersToInsert);

    Set<Role> addRoles(Set<Role> roles);

    Optional<User> getUserByUsername(String username);

    Role getRoleByName(String roleName);

    List<User> getAll();
}
