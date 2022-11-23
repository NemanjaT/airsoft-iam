package com.ntozic.airsoft.iam.controller.graphql;

import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @QueryMapping(name = "currentUser")
    public UserDto currentUser() {
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByReference(currentUser);
    }
}
