package com.project.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @NotBlank(message = "must not be blank")
    private String email;
    @NotBlank(message = "must not be blank")
    private String password;
    @NotBlank(message = "must not be blank")
    private String fullName;
}
