package com.ntozic.airsoft.iam.config.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import jakarta.annotation.Nullable;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AirsoftGrpcAuthenticationReader implements GrpcAuthenticationReader {
    @Nullable
    @Override
    public Authentication readAuthentication(ServerCall<?, ?> serverCall, Metadata metadata) throws AuthenticationException {
        return null;
    }
}
