package com.controller;

import com.configuration.jwt.JwtUtils;
import com.dto.request.validate.UserLoginDTO;
import com.dto.response.UserJWTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/abcflights/")
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<UserJWTResponse> doLogin(@RequestBody UserLoginDTO userEntry)throws Exception
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntry.getUsername(), userEntry.getPassword()));
        } catch (Exception e) {
            throw new Exception("Bad credentials ");
        }

        UserDetails userDetails =  userDetailsService.loadUserByUsername(userEntry.getUsername());

        String token = jwtUtils.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        UserJWTResponse userJWTResponse = new UserJWTResponse(userEntry.getUsername(), token, roles);

        return new ResponseEntity<>(userJWTResponse, HttpStatus.OK);
    }
}