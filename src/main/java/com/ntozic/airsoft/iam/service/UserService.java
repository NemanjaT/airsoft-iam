package com.ntozic.airsoft.iam.service;

import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.exception.UserAlreadyExistsException;
import com.ntozic.airsoft.iam.exception.UserNotFoundException;
import com.ntozic.airsoft.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaTemplate = kafkaTemplate;
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

    public List<UserDto> getUsersByReference(List<String> references) throws UserNotFoundException {
        return userRepository.findByReferenceIn(references)
                .stream().map(UserDto::fromEntity)
                .toList();
    }

    @Transactional
    public void create(UserDto user) {
        final var userModel = user.toEntity(passwordEncoder.encode(user.password()));
        try {
            userRepository.save(userModel);
            kafkaTemplate.sendDefault(userModel.getReference(), userModel);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistsException(format("User with email=%s already exists", user.email()));
        }
    }
}
