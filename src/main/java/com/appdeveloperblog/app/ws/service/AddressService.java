package com.appdeveloperblog.app.ws.service;

import com.appdeveloperblog.app.ws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddresses(String userId);

}
