package com.ntozic.airsoft.iam.config.graphql;

import com.ntozic.airsoft.iam.exception.UserAlreadyExistsException;
import com.ntozic.airsoft.iam.exception.UserNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof UserNotFoundException userNotFoundException) {
            return resolveUserNotFoundException(userNotFoundException, env);
        }
        if (ex instanceof UserAlreadyExistsException userAlreadyExistsException) {
            return resolveUserAlreadyExistsException(userAlreadyExistsException, env);
        }
        if (ex instanceof IllegalArgumentException illegalArgumentException) {
            return resolveIllegalArgumentException(illegalArgumentException, env);
        }
        return super.resolveToSingleError(ex, env);
    }

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof ConstraintViolationException convex) {
            return resolveConstraintViolationException(convex, env);
        }
        return super.resolveToMultipleErrors(ex, env);
    }

    public List<GraphQLError> resolveConstraintViolationException(
            ConstraintViolationException ex,
            DataFetchingEnvironment env) {
        return ex.getConstraintViolations().stream()
                .map(viol -> GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message(viol.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .location(env.getField().getSourceLocation())
                        .build())
                .toList();
    }

    private GraphQLError resolveUserAlreadyExistsException(UserAlreadyExistsException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message("error.userAlreadyExists")
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    private GraphQLError resolveUserNotFoundException(UserNotFoundException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.NOT_FOUND)
                .message("error.userNotFound")
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

    private GraphQLError resolveIllegalArgumentException(IllegalArgumentException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
