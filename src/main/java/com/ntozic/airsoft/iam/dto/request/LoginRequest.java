package com.ntozic.airsoft.iam.dto.request;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
