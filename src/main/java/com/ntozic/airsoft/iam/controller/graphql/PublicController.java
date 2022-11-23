package com.ntozic.airsoft.iam.controller.graphql;

import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Controller
public class PublicController {
    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping(name = "publicUserByReference")
    @PreAuthorize("isAnonymous() || isAuthenticated()")
    public UserDto publicUserByReference(@Argument String reference) {
        Assert.isTrue(StringUtils.hasLength(reference.trim()), "error.invalidReference");

        return userService.getUserByReference(reference);
    }

    @QueryMapping(name = "publicUsersByReference")
    @PreAuthorize("isAnonymous() || isAuthenticated()")
    public List<UserDto> publicUsersByReference(@Argument List<String> references) {
        Assert.notEmpty(references, "error.invalidReferences");
        references.forEach(r -> Assert.isTrue(StringUtils.hasLength(r.trim()), "error.invalidReference"));

        return userService.getUsersByReference(references);
    }
}
