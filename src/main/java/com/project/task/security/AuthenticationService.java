package com.project.task.security;

import com.project.task.dto.request.UserLoginRequestDto;
import com.project.task.dto.response.UserLoginResponseDto;
import com.project.task.dto.request.UserRegistrationRequestDto;
import com.project.task.dto.response.UserResponseDto;
import com.project.task.exception.RegistrationException;

public interface AuthenticationService {
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
