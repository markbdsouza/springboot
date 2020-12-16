package com.appdeveloperblog.app.ws.service;

import com.appdeveloperblog.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDto createUser(UserDto user);

    public UserDto getUser(String email);
}
