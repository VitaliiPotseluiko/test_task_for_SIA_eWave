package com.project.task.mapper.impl;

import com.project.task.dto.request.UserRegistrationRequestDto;
import com.project.task.dto.response.UserResponseDto;
import com.project.task.mapper.Mapper;
import com.project.task.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements
        Mapper<UserResponseDto, User, UserRegistrationRequestDto> {
    @Override
    public UserResponseDto toDto(User model) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(model.getId());
        responseDto.setEmail(model.getEmail());
        responseDto.setFullName(model.getFullName());
        return responseDto;
    }

    @Override
    public User toModel(UserRegistrationRequestDto requestDto) {
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setFullName(requestDto.getFullName());
        return user;
    }
}
