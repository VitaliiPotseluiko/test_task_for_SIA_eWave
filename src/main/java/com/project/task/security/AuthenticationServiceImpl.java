package com.project.task.security;

import com.project.task.dto.request.UserLoginRequestDto;
import com.project.task.dto.response.UserLoginResponseDto;
import com.project.task.dto.request.UserRegistrationRequestDto;
import com.project.task.dto.response.UserResponseDto;
import com.project.task.exception.RegistrationException;
import com.project.task.mapper.Mapper;
import com.project.task.model.Role;
import com.project.task.model.User;
import com.project.task.repository.RoleRepository;
import com.project.task.repository.UserRepository;
import com.project.task.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Mapper<UserResponseDto, User, UserRegistrationRequestDto> mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                        requestDto.getPassword())
        );
        String token = jwtUtil.generateToken(requestDto.getEmail());
        return new UserLoginResponseDto(token);
    }

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User is already registered!");
        }
        User user = mapper.toModel(requestDto);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.RoleName.USER));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return mapper.toDto(user);
    }
}
