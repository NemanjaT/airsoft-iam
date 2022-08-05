package com.ntozic.airsoft.iam.service;

import com.ntozic.airsoft.iam.dao.UserDto;
import com.ntozic.airsoft.iam.exception.UserNotFoundException;
import com.ntozic.airsoft.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .map(u -> new UserDto(u.getReference(), u.getEmail(), u.getPassword(), List.of(() -> "USER")))
                .orElseThrow(() -> new UserNotFoundException(format("User with email=%s not found", email)));
    }
}
