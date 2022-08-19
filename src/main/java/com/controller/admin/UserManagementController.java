package com.controller.admin;

import com.entity.User;
import com.service.UserService;
import com.wrapper.ListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/abcflights")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ListWrapper<List<User>>> getAllUsers(){
        List<User> customUserList = userService.getAll();
        return new ResponseEntity<>(new ListWrapper<>((long) customUserList.size(), customUserList), HttpStatus.OK);
    }

}