package com.basicforum.controller;

import com.basicforum.Exception.UserAlreadyExists;
import com.basicforum.Exception.UserNotFound;
import com.basicforum.model.AuthenticationRequest;
import com.basicforum.model.AuthenticationResponse;
import com.basicforum.model.RegisterRequest;
import com.basicforum.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registrationRequest) {
        try{
            var response=authenticationService.register(registrationRequest);
            return ResponseEntity.ok(response);
        }catch (UserAlreadyExists e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest registrationRequest) {
        try {
            var response=authenticationService.authenticate(registrationRequest);
            return ResponseEntity.ok(response);
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
