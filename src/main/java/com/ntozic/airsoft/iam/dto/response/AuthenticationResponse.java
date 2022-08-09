package com.ntozic.airsoft.iam.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public record AuthenticationResponse(String token, LocalDateTime expiredAt) implements Serializable {
}
