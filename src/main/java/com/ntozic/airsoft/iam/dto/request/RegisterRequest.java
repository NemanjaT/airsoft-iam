package com.ntozic.airsoft.iam.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ntozic.airsoft.iam.dto.UserDto;
import com.ntozic.airsoft.iam.dto.UserStatus;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Jacksonized
public record RegisterRequest (
        @NotNull(message = "error.firstName.empty")
        @Size(min = 2, message = "error.firstName.size")
        String firstName,

        @NotNull(message = "error.lastName.empty")
        @Size(min = 2, message = "error.lastName.size")
        String lastName,

        @NotNull(message = "error.email.empty")
        @Email(message = "error.email.invalid")
        String email,
        String address,
        String city,

        @NotNull(message = "error.countryCode.empty")
        @Size(min = 2, max = 2, message = "error.countryCode.size")
        String countryCode,

        @NotNull(message = "error.dateOfBirth.empty")
        @PastOrPresent(message = "error.dateOfBirth.pastOrPresent")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        LocalDate dateOfBirth,

        @NotNull(message = "error.password1.empty")
        @Size(min = 8, message = "error.password1.size")
        String password1,

        @NotNull(message = "error.password2.empty")
        @Size(min = 8, message = "error.password2.size")
        String password2
) implements Serializable {
    @AssertTrue(message = "error.password1.invalid")
    public boolean passwordCriteria() {
        return password1.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    @AssertTrue(message = "error.password.mismatch")
    public boolean passwordsMatch() {
        return password1.equals(password2);
    }

    public UserDto toDto() {
        return UserDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .city(city)
                .countryCode(countryCode)
                .password(password1)
                .status(UserStatus.ACTIVE)
                .build();
    }
}
