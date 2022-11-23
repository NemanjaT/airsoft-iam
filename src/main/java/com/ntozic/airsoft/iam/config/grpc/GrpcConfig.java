package com.ntozic.airsoft.iam.config.grpc;

import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    @Bean
    public GrpcAuthenticationReader authenticationReader() {
        return new AirsoftGrpcAuthenticationReader();
    }
}
