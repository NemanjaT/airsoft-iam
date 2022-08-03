package com.ntozic.airsoft.iam;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TokenGeneratorTest {
    private Logger logger = LoggerFactory.getLogger(TokenGeneratorTest.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void generateToken() {
        var password = "password";
        var result = passwordEncoder.encode(password);
        logger.info(result);
    }
}
