package com.appdeveloperblog.app.ws.security;

import com.appdeveloperblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
    public static long EXPIRATION_TIME = 86400000;
    public static String TOKEN_PREFIX = "Bearer ";
    public static String HEADER_STRING = "Authorization";
    public static String SIGNUP_URL = "/users";
    public static String HEADER_NAME = "User ID";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
