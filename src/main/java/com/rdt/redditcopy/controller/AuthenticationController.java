package com.rdt.redditcopy.controller;

import com.rdt.redditcopy.request.AuthenticationRequest;
import com.rdt.redditcopy.request.RegisterRequest;
import com.rdt.redditcopy.response.AuthenticationResponse;
import com.rdt.redditcopy.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register/")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/login/")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
