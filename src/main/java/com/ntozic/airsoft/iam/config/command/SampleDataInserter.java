package com.ntozic.airsoft.iam.config.command;

import com.ntozic.airsoft.iam.model.User;
import com.ntozic.airsoft.iam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataInserter implements CommandLineRunner {
    private final UserRepository userRepository;

    @Autowired
    public SampleDataInserter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        createIfDoestExists(User.builder()
                .email("nemanja.tozic@email.com")
                .firstName("Nemanja")
                .lastName("Tozic")
                .address("Šumadijska 30")
                .city("Beograd")
                .countryCode("RS")
                .reference("08819509-f813-43ed-9200-b1436d88f85d")
                .password("$2a$10$oMU9KUm6gOqte348xLEyU.Nbst1XJc7jYpSShhtcN7laNqFAFpzOW") // "password"
                .build());
    }

    private void createIfDoestExists(User user) {
       userRepository.findByEmail(user.getEmail())
               .orElseGet(() -> userRepository.save(user));
    }
}
