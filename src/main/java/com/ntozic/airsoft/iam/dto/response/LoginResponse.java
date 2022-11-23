package com.ntozic.airsoft.iam.dto.response;

import java.io.Serializable;
import java.time.ZonedDateTime;

public record LoginResponse(
        String reference,
        String token,
        ZonedDateTime expiresAt
) implements Serializable {
}
