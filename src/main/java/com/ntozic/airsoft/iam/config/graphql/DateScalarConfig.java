package com.ntozic.airsoft.iam.config.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;

@Configuration
public class DateScalarConfig {
    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description(LocalDate.class.getName())
                .coercing(new Coercing<LocalDate, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof LocalDate) {
                            return dataFetcherResult.toString();
                        }
                        throw new CoercingSerializeException("Expected a LocalDate object.");
                    }

                    @Override
                    public LocalDate parseValue(Object input) throws CoercingParseValueException {
                        try {
                            if (input instanceof String val) {
                                return LocalDate.parse(val, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            }
                            throw new CoercingParseValueException(
                                    format("Expected a String, but got %s.", input.getClass().getName()));
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(
                                    format("Not a valid date in value %s", input.toString()), e);
                        }
                    }

                    @Override
                    public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
                        if (input instanceof StringValue strVal) {
                            return this.parseValue(strVal.getValue());
                        }
                        throw new CoercingParseLiteralException(
                                format("Expected a StringValue, but got %s.", input.getClass().getName()));
                    }
                })
                .build();
    }
}
