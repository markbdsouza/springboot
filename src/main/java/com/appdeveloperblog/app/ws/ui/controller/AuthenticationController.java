package com.appdeveloperblog.app.ws.ui.controller;

import com.appdeveloperblog.app.ws.ui.model.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Only implemented for Swagger
@RestController
public class AuthenticationController {

    // this login is implemented only to trick Springboot to load the swagger config below.
    // but when we actually hit it, it goes to getAuthenticationFilter() in WebSecurity and runs that code
    @ApiOperation("User login")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT Token>",
                                    response = String.class),
                            @ResponseHeader(name = "userId",
                                    description = "<public user id value>",
                                    response = String.class)
                    })
    }
    )
    @PostMapping("/users/login")
    public void fakeLogin(@RequestBody LoginRequestModel loginRequestModel) {
        throw new IllegalStateException("test");
    }
}
