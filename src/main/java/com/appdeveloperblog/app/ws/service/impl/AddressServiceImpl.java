package com.appdeveloperblog.app.ws.service.impl;

import com.appdeveloperblog.app.ws.io.Repository.AddressRepository;
import com.appdeveloperblog.app.ws.io.Repository.UserRepository;
import com.appdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import com.appdeveloperblog.app.ws.service.AddressService;
import com.appdeveloperblog.app.ws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;
        List<AddressEntity> addressEntities = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity entity: addressEntities){
            returnValue.add(modelMapper.map(entity, AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String userId, String addressId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(addressEntity, AddressDto.class);
    }
}
