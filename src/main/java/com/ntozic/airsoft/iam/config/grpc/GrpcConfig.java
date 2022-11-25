package com.ntozic.airsoft.iam.config.grpc;

import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// TODO Currently the library didn't upgrade for spring boot 6. remove once done (current version is 2.14.0.RELEASE)
// https://github.com/yidongnan/grpc-spring-boot-starter/issues/778
@ImportAutoConfiguration({
        // grpc-client-spring-boot-autoconfiguration
        net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientMetricAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientHealthAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientSecurityAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientTraceAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcDiscoveryClientAutoConfiguration.class,
        // grpc-common-spring-boot
        net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration.class,
        net.devh.boot.grpc.common.autoconfigure.GrpcCommonTraceAutoConfiguration.class,
        // grpc-server-spring-boot-autoconfiguration
        net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcHealthServiceAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataConsulConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataEurekaConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataNacosConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataZookeeperConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcReflectionServiceAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerMetricAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerTraceAutoConfiguration.class,
})
public class GrpcConfig {
    @Bean
    public GrpcAuthenticationReader authenticationReader() {
        return new AirsoftGrpcAuthenticationReader();
    }
}
