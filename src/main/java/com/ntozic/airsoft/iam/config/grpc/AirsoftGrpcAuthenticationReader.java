package com.ntozic.airsoft.iam.config.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Nullable;

public class AirsoftGrpcAuthenticationReader implements GrpcAuthenticationReader {
    @Nullable
    @Override
    public Authentication readAuthentication(ServerCall<?, ?> serverCall, Metadata metadata) throws AuthenticationException {
        return null;
    }
}
