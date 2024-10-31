package com.davy.quezzy.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserControllerTests {

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

    @BeforeEach
    @Sql(statements = "DELETE FROM users")
    @Transactional
    public void clearDatabase() {}

    private String mockValidUser() {
        return "{ \"username\": \"davy123\", \"email\": \"davy@gmail.com\", \"password\": \"senha123\" }";
    }

    private String mockUpdatedUser() {
        return "{ \"username\": \"davy456\", \"email\": \"davy456@gmail.com\", \"password\": \"senha456\" }";
    }

    @Test
    public void testListAllUsers() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201);
    
        given()
            .when()
            .get("/api/users")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].username", notNullValue())
            .body("[0].email", notNullValue());
    }

    @Test
    public void testReturnEmptyWhenNoUsers() {
        given()
            .when()
            .get("/api/users")
            .then()
            .statusCode(200)
            .body("size()", equalTo(0));
    }

    @Test
    public void testCreateUserWithMissingFields() {
        String userWithMissingFields = "{ \"username\": \"\", \"email\": \"\", \"password\": \"\" }";

        given()
            .contentType(ContentType.JSON)
            .body(userWithMissingFields)
            .when()
            .post("/api/users")
            .then()
            .statusCode(400)
            .body(equalTo("Todos os campos são obrigatórios"));
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        String userWithInvalidEmail = "{ \"username\": \"davy123\", \"email\": \"invalid-email\", \"password\": \"senha123\" }";

        given()
            .contentType(ContentType.JSON)
            .body(userWithInvalidEmail)
            .when()
            .post("/api/users")
            .then()
            .statusCode(400)
            .body(equalTo("Email inválido"));
    }

    @Test
    public void testCreateValidUser() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201);
    }

    @Test
    public void testGetUserById() {
        int userId = given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .when()
            .get("/api/users/" + userId)
            .then()
            .statusCode(200)
            .body("id", equalTo(userId))
            .body("username", equalTo("davy123"))
            .body("email", equalTo("davy@gmail.com"));
    }

    @Test
    public void testGetUserByIdNotFound() {
        given()
            .when()
            .get("/api/users/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testUpdateUser() {
        int userId = given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .contentType(ContentType.JSON)
            .body(this.mockUpdatedUser())
            .when()
            .put("/api/users/" + userId)
            .then()
            .statusCode(200)
            .body("id", equalTo(userId))
            .body("username", equalTo("davy456"))
            .body("email", equalTo("davy456@gmail.com"));
    }

    @Test
    public void testUpdateUserNotFound() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockUpdatedUser())
            .when()
            .put("/api/users/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testUpdateUserWithMissingFields() {
        int userId = given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        String userWithMissingFields = "{ \"username\": \"\", \"email\": \"\", \"password\": \"\" }";

        given()
            .contentType(ContentType.JSON)
            .body(userWithMissingFields)
            .when()
            .put("/api/users/" + userId)
            .then()
            .statusCode(400)
            .body(equalTo("Todos os campos são obrigatórios"));
    }

    @Test
    public void testUpdateUserWithInvalidEmail() {
        int userId = given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        String userWithInvalidEmail = "{ \"username\": \"davy456\", \"email\": \"invalid-email\", \"password\": \"senha456\" }";

        given()
            .contentType(ContentType.JSON)
            .body(userWithInvalidEmail)
            .when()
            .put("/api/users/" + userId)
            .then()
            .statusCode(400)
            .body(equalTo("Email inválido"));
    }

    @Test
    public void testDeleteUser() {
        int userId = given()
            .contentType(ContentType.JSON)
            .body(this.mockValidUser())
            .when()
            .post("/api/users")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .when()
            .delete("/api/users/" + userId)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/api/users/" + userId)
            .then()
            .statusCode(404);
    }

    @Test
    public void testDeleteUserNotFound() {
        given()
            .when()
            .delete("/api/users/9999")
            .then()
            .statusCode(404);
    }
}
