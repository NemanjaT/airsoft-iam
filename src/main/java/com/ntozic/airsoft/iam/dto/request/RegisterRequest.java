package com.ntozic.airsoft.iam.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ntozic.airsoft.iam.dto.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Jacksonized
@Data
public class RegisterRequest implements Serializable {
    @NotNull
    @Size(min = 2)
    private String firstName;

    @NotNull
    @Size(min = 2)
    private String lastName;

    @Email
    @NotNull
    private String email;

    private String address;

    private String city;

    @NotNull
    @Size(min = 2, max = 2)
    private String countryCode;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    @Size(min = 8)
    private String passwordAgain;

    @AssertTrue(message = "Passwords do not match")
    private boolean passwordsMatch() {
        return password.equals(passwordAgain);
    }

    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .city(city)
                .countryCode(countryCode)
                .password(password)
                .build();
    }
}
