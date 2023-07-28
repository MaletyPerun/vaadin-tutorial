package com.example.application.data.dto;

import com.example.application.data.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

import static com.example.application.data.util.DateTimeUtil.DATE_TIME_PATTERN;

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
    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    protected LocalDate birthday;
    @NotEmpty
    protected String email;
    @NotEmpty
    protected String phone;
    protected boolean isAdmin;


}
