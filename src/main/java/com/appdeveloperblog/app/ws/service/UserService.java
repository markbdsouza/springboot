package com.appdeveloperblog.app.ws.service;

import com.appdeveloperblog.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

// extends UserDetailsService to get access to the loadByUsername method which is used by Spring security.
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto getUserByUserId(String id);

    UserDto updateUser(UserDto userDto, String id);

    void deleteUser(String id);

    List<UserDto> getUserList(int page, int limit);
}
