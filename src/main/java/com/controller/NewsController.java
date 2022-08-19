package com.controller;

import com.wrapper.GenericResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/abcflights")
public class NewsController {
    @GetMapping("/news")
    public GenericResponse<List<String>> getAllNews(Authentication authentication){
        List<String> authorities = authentication != null ? authentication.getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList()) : null;
        return new GenericResponse<>(authorities, Arrays.asList("NEWS 1", "NEWS 2"));
    }

}