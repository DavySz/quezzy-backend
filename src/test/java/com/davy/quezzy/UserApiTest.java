package com.davy.quezzy;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserApiTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @BeforeEach
    public void setUpPort() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateValidUser() {
        String newUser = "{ \"username\": \"davy123\", \"email\": \"davy@gmail.com\", \"password\": \"senha123\" }";

        given()
            .contentType(ContentType.JSON)
            .body(newUser)
            .when()
            .post("/api/users")
            .then()
            .statusCode(201);
    }
}
