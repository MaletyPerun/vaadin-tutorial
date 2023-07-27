package com.example.application.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    protected long id;
    protected String firstName;
    protected String lastName;
    protected String patronymic;
    protected String birthday;
    protected String email;
    protected String phone;
    protected String roles;
}
