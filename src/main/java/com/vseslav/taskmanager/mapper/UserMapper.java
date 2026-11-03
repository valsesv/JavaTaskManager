package com.vseslav.taskmanager.mapper;

import com.vseslav.taskmanager.dto.UserDTO;
import com.vseslav.taskmanager.entity.User;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        return user;
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(user.getId(), user.getName());
    }
}
