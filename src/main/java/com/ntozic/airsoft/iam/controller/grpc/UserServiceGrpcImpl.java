package com.ntozic.airsoft.iam.controller.grpc;

import com.ntozic.airsoft.grpc.UserRequest;
import com.ntozic.airsoft.grpc.UserResponse;
import com.ntozic.airsoft.grpc.UserServiceGrpc;
import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.dto.UserStatus;
import com.ntozic.airsoft.iam.service.UserService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@GrpcService
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;

    @Autowired
    public UserServiceGrpcImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void getByReference(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        final var user = userService.getUserByReference(request.getReference());
        responseObserver.onNext(this.toUserResponse(user));
        responseObserver.onCompleted();
    }

    private UserResponse toUserResponse(UserDto user) {
        final var builder = UserResponse.newBuilder();
        builder.setReference(user.reference());
        builder.setEmail(user.email());
        builder.setFirstName(user.firstName());
        builder.setLastName(user.lastName());
        Optional.ofNullable(user.address()).ifPresent(builder::setAddress);
        Optional.ofNullable(user.city()).ifPresent(builder::setCity);
        Optional.ofNullable(user.countryCode()).ifPresent(builder::setCountryCode);
        Optional.ofNullable(user.dateOfBirth())
                .map(dob -> dob.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .ifPresent(builder::setDateOfBirth);
        Optional.of(user.status())
                .map(UserStatus::name)
                .ifPresent(builder::setStatus);
        return builder.build();
    }
}
