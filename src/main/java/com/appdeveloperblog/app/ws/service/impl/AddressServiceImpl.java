package com.appdeveloperblog.app.ws.service.impl;

import com.appdeveloperblog.app.ws.service.AddressService;
import com.appdeveloperblog.app.ws.shared.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    @Autowired

    @Override
    public List<AddressDto> getAddresses(String userId) {
        return null;
    }
}
