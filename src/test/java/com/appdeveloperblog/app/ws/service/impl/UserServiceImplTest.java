package com.appdeveloperblog.app.ws.service.impl;

import com.appdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appdeveloperblog.app.ws.io.Repository.UserRepository;
import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import com.appdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;
import com.appdeveloperblog.app.ws.shared.dto.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    Utils utils;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String USER_ID = "123";
    String ENCRYPTED_PASSWORD = "AS2D5X1ZV";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getUserForValidUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setFirstName("FIRST NAME");
        userEntity.setEncryptedPassword(ENCRYPTED_PASSWORD);
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto returnValue = userService.getUser("test@email.com");
        assertNotNull(returnValue);
        assertEquals(returnValue.getFirstName(), userEntity.getFirstName());
    }

    @Test
    void getUserForNullParameter() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, ()-> userService.getUser("test@gmail.com"));
    }

    @Test
    void createUser(){
        UserDto mockUser = new UserDto();
        mockUser.setEmail("ASD.@asd.com");
        List<AddressDto> addressDtoList = new ArrayList<>();
        AddressDto address = new AddressDto();
        address.setCity("Cochin");
        addressDtoList.add(address);
        mockUser.setAddresses(addressDtoList);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setLastName("Last Name");
        when(utils.generateAddressId(anyInt())).thenReturn("ASDA123SD");
        when(utils.generateUserId(anyInt())).thenReturn(USER_ID);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);


        UserDto returnValue = userService.createUser(mockUser);
        assertNotNull(returnValue);
        assertEquals(returnValue.getFirstName(), "First Name");
        assertEquals(returnValue.getLastName(), "Last Name");
        verify(utils, times(1)).generateAddressId(anyInt());

    }

    @Test
    void createUserWithException()
    {        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setLastName("Last Name");
        UserDto mockUser = new UserDto();
        mockUser.setEmail("asd");
        mockUser.setFirstName("ASD");
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        assertThrows(UserServiceException.class ,
                ()-> userService.createUser(mockUser));
    }

}