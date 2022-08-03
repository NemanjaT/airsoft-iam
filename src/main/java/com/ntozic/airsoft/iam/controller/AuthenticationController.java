package com.ntozic.airsoft.iam.controller;

import com.ntozic.airsoft.iam.config.auth.JwtTokenUtil;
import com.ntozic.airsoft.iam.dao.request.AuthenticationRequest;
import com.ntozic.airsoft.iam.dao.response.AuthenticationResponse;
import com.ntozic.airsoft.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var userDetails = userService.getUserByEmail(request.getEmail());
        var token = jwtTokenUtil.generateToken(userDetails);
        var expirationTime = jwtTokenUtil.getExpiration(token);
        return ResponseEntity.ok(new AuthenticationResponse(
                token,
                expirationTime.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime())
        );
    }
}
