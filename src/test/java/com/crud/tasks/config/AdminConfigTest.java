package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "admin.mail=test@mail.com"
})
class AdminConfigTest {

    @Autowired
    private AdminConfig adminConfig;

    @Test
    void testAdminConfig() {
        // When
        String result = adminConfig.getAdminMail();
        // Then
       assertEquals("test@mail.com", result);
    }
}