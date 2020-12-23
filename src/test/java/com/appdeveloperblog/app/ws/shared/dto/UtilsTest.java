package com.appdeveloperblog.app.ws.shared.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp(){

    }
    @Test
    void generateUserId() {
        int LENGTH = 20;
        String generatedId = utils.generateUserId(LENGTH);
        String generatedId2 = utils.generateUserId(LENGTH);
        assertNotNull(generatedId);
        assertTrue(generatedId.length()==LENGTH);
        assertTrue(generatedId != generatedId2);
    }
}