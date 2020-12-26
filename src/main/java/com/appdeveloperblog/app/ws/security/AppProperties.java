package com.appdeveloperblog.app.ws.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// This component is created as a Bean in the main App Class
@Component
public class AppProperties {
    @Autowired
    private Environment env;

    public String getTokenSecret(){
        //picks from application.properties
        return env.getProperty("tokenSecret");
    }

}
