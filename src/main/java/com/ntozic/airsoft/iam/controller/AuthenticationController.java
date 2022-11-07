package com.ntozic.airsoft.iam.controller;

import com.ntozic.airsoft.iam.config.auth.JwtTokenUtil;
import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.dto.request.AuthenticationRequest;
import com.ntozic.airsoft.iam.dto.request.RegisterRequest;
import com.ntozic.airsoft.iam.dto.response.AuthenticationResponse;
import com.ntozic.airsoft.iam.exception.UserNotFoundException;
import com.ntozic.airsoft.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        try {
            var user = userService.getUserByEmail(request.email());
            var token = jwtTokenUtil.generateToken(user);
            var expirationTime = jwtTokenUtil.getExpiration(token);
            return ResponseEntity.ok(
                    new AuthenticationResponse(
                            token,
                            expirationTime.toInstant().atZone(ZoneOffset.UTC)
                    )

            );
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        userService.create(request.toDto());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users-by-ref")
    public ResponseEntity<List<UserDto>> getUsersByRef(@RequestBody List<String> refs) {
        return ResponseEntity.ok(refs.stream().map(userService::getUserByReference).collect(Collectors.toList()));
    }
}
