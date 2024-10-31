package com.davy.quezzy.helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EmailValidatorTests {

    @Test
    public void testValidEmails() {
        assertTrue(EmailValidator.isValid("test@example.com"));
        assertTrue(EmailValidator.isValid("user.name+tag+sorting@example.com"));
        assertTrue(EmailValidator.isValid("user_name@example.co.uk"));
        assertTrue(EmailValidator.isValid("user-name@example.org"));
        assertTrue(EmailValidator.isValid("user.name@example.com"));
    }

    @Test
    public void testInvalidEmails() {
        assertFalse(EmailValidator.isValid("plainaddress"));
        assertFalse(EmailValidator.isValid("@missingusername.com"));
        assertFalse(EmailValidator.isValid("username@.com"));
        assertFalse(EmailValidator.isValid("username@com"));
    }
}