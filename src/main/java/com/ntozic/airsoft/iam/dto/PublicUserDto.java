package com.ntozic.airsoft.iam.dto;

import lombok.Builder;

@Builder
public record PublicUserDto(
        String reference,
        String firstName,
        String lastName
) {
    public static PublicUserDto fromUserDto(UserDto userDto) {
        return new PublicUserDto(
                userDto.reference(),
                userDto.firstName(),
                userDto.lastName()
        );
    }
}
