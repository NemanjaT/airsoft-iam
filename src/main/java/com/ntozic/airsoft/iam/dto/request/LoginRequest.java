package com.ntozic.airsoft.iam.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Builder
@Jacksonized
public record LoginRequest(
        @NotNull(message = "error.email.empty")
        @Email(message = "error.email.invalid")
        String email,

        @NotNull(message = "error.password.empty")
        @Size(min = 8, message = "error.password.size")
        String password
) implements Serializable {
}
