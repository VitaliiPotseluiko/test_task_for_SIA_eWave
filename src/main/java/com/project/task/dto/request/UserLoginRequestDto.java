package com.project.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "must not be blank")
    private String email;
    @NotBlank(message = "must not be blank")
    private String password;
}
