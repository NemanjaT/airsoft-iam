package com.ntozic.airsoft.iam.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Builder
@Jacksonized
public record AuthenticationRequest(String email, String password) implements Serializable {
}