package com.ntozic.airsoft.iam.service;

import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.exception.UserNotFoundException;
import com.ntozic.airsoft.iam.model.User;
import com.ntozic.airsoft.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(format("User with email=%s not found", email)));
    }

    public UserDto getUserByReference(String reference) throws UserNotFoundException {
        return userRepository.findByReference(reference)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(format("User with reference=%s not found", reference)));
    }

    public void create(UserDto user) {
        userRepository.save(user.toEntity(passwordEncoder.encode(user.password())));
    }
}
