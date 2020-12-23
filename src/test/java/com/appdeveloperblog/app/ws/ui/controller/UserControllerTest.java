package com.appdeveloperblog.app.ws.ui.controller;

import com.appdeveloperblog.app.ws.service.AddressService;
import com.appdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;
import com.appdeveloperblog.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @InjectMocks
    UserController userController;

    UserDto userDto;

    @Mock
    UserServiceImpl userService;

    @Mock
    AddressService addressService;

    final String USER_ID = "12FO)QW$";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = new UserDto();
        userDto.setFirstName("FIRST NAME");
        userDto.setEmail("ASD");
        userDto.setUserId(USER_ID);
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);
        assertNotNull(userRest);
        assertEquals(userRest.getFirstName(), "FIRST NAME");
        assertEquals(userRest.getUserId(), USER_ID);
    }
}