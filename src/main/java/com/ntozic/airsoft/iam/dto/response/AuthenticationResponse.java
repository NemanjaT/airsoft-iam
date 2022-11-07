package com.ntozic.airsoft.iam.dto.response;

import java.io.Serializable;
import java.time.ZonedDateTime;

public record AuthenticationResponse(
        String token,
        ZonedDateTime expiredAt
) implements Serializable {
}
