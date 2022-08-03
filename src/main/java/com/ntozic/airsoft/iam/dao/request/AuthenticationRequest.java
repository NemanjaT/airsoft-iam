package com.ntozic.airsoft.iam.dao.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AuthenticationRequest {
    String email;
    String password;
}
