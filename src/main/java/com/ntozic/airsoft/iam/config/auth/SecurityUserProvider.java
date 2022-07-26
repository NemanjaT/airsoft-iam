package com.ntozic.airsoft.iam.config.auth;

import com.ntozic.airsoft.iam.exception.UserNotFoundException;
import com.ntozic.airsoft.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class SecurityUserProvider implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public SecurityUserProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userService.getUserByEmail(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(format("Username %s not found", username), e);
        }
    }
}
