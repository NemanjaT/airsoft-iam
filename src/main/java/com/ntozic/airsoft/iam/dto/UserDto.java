package com.ntozic.airsoft.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntozic.airsoft.iam.model.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record UserDto (
        String reference,
        String email,
        String password,
        String firstName,
        String lastName,
        String address,
        String city,
        String countryCode,
        LocalDate dateOfBirth,
        UserStatus status,
        List<GrantedAuthority> authorities
) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != UserStatus.BLOCKED;
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .authorities(List.of(() -> "USER"))
                .reference(user.getReference())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .city(user.getCity())
                .countryCode(user.getCountryCode())
                .dateOfBirth(user.getDateOfBirth())
                .password(user.getPassword())
                .status(user.getStatus())
                .build();
    }

    public User toEntity(String password) {
        return User.builder()
                .reference(Optional.ofNullable(reference).orElse(UUID.randomUUID().toString()))
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .address(address)
                .city(city)
                .countryCode(countryCode)
                .dateOfBirth(dateOfBirth)
                .password(password)
                .status(status)
                .build();
    }
}
