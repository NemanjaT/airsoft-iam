package com.ntozic.airsoft.iam.controller.graphql;

import com.ntozic.airsoft.iam.config.auth.JwtTokenUtil;
import com.ntozic.airsoft.iam.dto.request.LoginRequest;
import com.ntozic.airsoft.iam.dto.request.RegisterRequest;
import com.ntozic.airsoft.iam.dto.response.LoginResponse;
import com.ntozic.airsoft.iam.dto.response.OperationResultResponse;
import com.ntozic.airsoft.iam.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.time.ZoneOffset;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @MutationMapping(name = "login")
    @PreAuthorize("isAnonymous()")
    public LoginResponse login(@Argument @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        var user = userService.getUserByEmail(request.email());
        var token = jwtTokenUtil.generateToken(user);
        var expirationTime = jwtTokenUtil.getExpiration(token);
        var expirationTimeInstant = expirationTime.toInstant().atZone(ZoneOffset.UTC);
        return new LoginResponse(user.reference(), token, expirationTimeInstant);
    }

    @MutationMapping(name = "register")
    @PreAuthorize("isAnonymous()")
    public OperationResultResponse register(@Argument @Valid RegisterRequest request) {
        Assert.isTrue(request.passwordCriteria(), "error.password1.invalid");
        Assert.isTrue(request.passwordsMatch(), "error.password.mismatch");

        userService.create(request.toDto());
        return new OperationResultResponse(true);
    }
}
