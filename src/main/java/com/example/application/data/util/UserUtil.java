package com.example.application.data.util;

import com.example.application.data.dto.UserDto;
import com.example.application.data.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtil {
    public User dtoToUser(UserDto userDto) {
        // TODO: 26.07.2023 здесь возможно ошибка при генерации ID новогопользователя
        // TODO: 28.07.2023 протеститровать через конструткор+Abstract Class и Builder+id в User
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPatronymic(),
                userDto.getBirthday(),
                userDto.getEmail(),
                userDto.getPhone(),
                userDto.getRoles()
                );
//                User.builder()
//                .id
//                .firstName(userDto.getFirstName())
//                .lastName(userDto.getLastName())
//                .patronymic(userDto.getPatronymic())
//                .birthday(userDto.getBirthday())
//                .email(userDto.getEmail())
//                .phone(userDto.getPhone())
                // TODO: 27.07.2023 игра с ролью при создании и редактирвании нового пользователя
//                .role(Role.USER)
//                .build();
    }

    public UserDto userToDto(User user) {
        // TODO: 26.07.2023 здесь возможно ошибка при генерации ID новогопользователя
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(user.getRoles())
                .build();
    }

//    public List<UserDto>
}
