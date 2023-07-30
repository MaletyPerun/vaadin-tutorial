package com.example.application.data.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PersonDto {

    protected long id;
    @NotEmpty
    protected String firstName;
    @NotEmpty
    protected String lastName;
    protected String patronymic;
    @NotNull
    protected LocalDate birthday;
    @NotEmpty
    protected String email;
    @NotEmpty
    protected String phone;
    protected boolean isAdmin;
}
