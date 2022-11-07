package com.ntozic.airsoft.iam.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Jacksonized
public record AuthenticationRequest(
        @NotNull(message = "auth.error.emptyEmail")
        @Email(message = "auth.error.invalidEmail")
        String email,
        @NotNull(message = "auth.error.emptyPassword")
        @Size(min = 8, message = "auth.error.sizePassword")
        String password
) implements Serializable {
}
