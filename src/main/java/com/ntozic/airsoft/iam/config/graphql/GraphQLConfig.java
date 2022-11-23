package com.ntozic.airsoft.iam.config.graphql;

import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {
    @Bean
    @Autowired
    public RuntimeWiringConfigurer runtimeWiringConfigurer(GraphQLScalarType dateScalar) {
        return builder -> {
            builder.scalar(dateScalar);
        };
    }
}
