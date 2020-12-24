package com.appdeveloperblog.app.ws.ui.controller;

import com.appdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appdeveloperblog.app.ws.service.AddressService;
import com.appdeveloperblog.app.ws.service.UserService;
import com.appdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;
import com.appdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users") //http://localhost:8080/users/
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    //add desc to swaggerj
    @ApiOperation(value = "get user details web service end point",
            notes = "This web service end point returns user details. Use public user id in the path")
    //Api Implicit Params is for swagger to add authroization as an input field
    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
    public List<UserRest> getUserList(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<UserRest> returnList = new ArrayList<>();
        List<UserDto> userDtoList = userService.getUserList(page, limit);
        UserRest userRest;
        for (UserDto userDto : userDtoList) {
            userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnList.add(userRest);
        }
        return returnList;
    }
    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {
        UserRest returnValue ;
        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetails, userDto);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createdUser, returnValue);
        returnValue = modelMapper.map(createdUser, UserRest.class);
        return returnValue;
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updatedUser = userService.updateUser(userDto, id);
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatus deleteUser(@PathVariable String id) {
        OperationStatus returnValue = new OperationStatus();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @GetMapping(path = "/{id}/addresses", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CollectionModel<AddressesRest> getAddresses(@PathVariable String id) {
        List<AddressesRest> returnValue = new ArrayList<>();
        List<AddressDto> addressDtoList = addressService.getAddresses(id);
        if(addressDtoList !=null && !addressDtoList.isEmpty()){
            ModelMapper modelMapper = new ModelMapper();
            Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
            returnValue = modelMapper.map(addressDtoList, listType);
            for(AddressesRest addressesRest: returnValue){
                Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getSingleAddress(id, addressesRest.getAddressId()))
                        .withSelfRel();
                addressesRest.add(selfLink);
            }
        }
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddresses(id))
                .withSelfRel();
        return CollectionModel.of(returnValue, userLink,selfLink);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "authorization", value="${userController.authorizationHeader.description}", paramType = "header")  )
    @GetMapping(path = "/{id}/addresses/{addressId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AddressesRest getSingleAddress(@PathVariable String id, @PathVariable String addressId){
        AddressesRest addressesRest;
        ModelMapper modelMapper = new ModelMapper();
        AddressDto addressDto = addressService.getAddress(id, addressId);

        addressesRest= modelMapper.map(addressDto, AddressesRest.class);
        //create links for HATEOS support
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
        Link addressLink =WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(id)
                .slash("addresses")
                .withRel("addresses");
        Link addressLink2 =WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddresses(addressId))
                .withRel("addresses");
        Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(id)
                .slash("addresses")
                .slash(addressId)
                .withSelfRel();
        addressesRest.add(userLink);
        addressesRest.add(addressLink);
        addressesRest.add(selfLink);

        // return EntityModel.of(addressesRest, Arrays.asList(userLink, addressLink, selfLink));
        return addressesRest;
    }
}

