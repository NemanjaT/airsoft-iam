package com.ntozic.airsoft.iam.dao.response;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AuthenticationResponse {
    String token;
    LocalDateTime expiredAt;
}
