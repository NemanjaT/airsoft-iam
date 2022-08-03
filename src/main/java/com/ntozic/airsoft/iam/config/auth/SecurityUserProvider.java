package com.ntozic.airsoft.iam.config.auth;

import com.ntozic.airsoft.iam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserProvider implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public SecurityUserProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByEmail(username);
    }
}
