package com.davy.quezzy.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
public class CategoryControllerTests {
    
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
    @Sql(statements = "DELETE FROM categories")
    @Transactional
    public void clearDatabase() {}

    private String mockCategory() {
        return "{ \"name\": \"Historia\" }";
    }

    private String mockUpdatedCategory() {
        return "{ \"name\": \"Geografia\" }";
    }

    @Test
    public void testListAllCategories() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockCategory())
            .when()
            .post("/api/categories")
            .then()
            .statusCode(201);
    
        given()
            .when()
            .get("/api/categories")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].name", notNullValue());
    }

    @Test
    public void testReturnEmptyWhenNoCategories() {
        given()
            .when()
            .get("/api/categories")
            .then()
            .statusCode(200)
            .body("size()", equalTo(0));
    }

    @Test
    public void testCreateCategory() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockCategory())
            .when()
            .post("/api/categories")
            .then()
            .statusCode(201)
            .body("name", equalTo("Historia"));
    }

    @Test
    public void testGetCategoryById() {
        int categoryId = given()
            .contentType(ContentType.JSON)
            .body(this.mockCategory())
            .when()
            .post("/api/categories")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .when()
            .get("/api/categories/" + categoryId)
            .then()
            .statusCode(200)
            .body("id", equalTo(categoryId))
            .body("name", equalTo("Historia"));
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        given()
            .when()
            .get("/api/categories/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testUpdateCategory() {
        int categoryId = given()
            .contentType(ContentType.JSON)
            .body(this.mockCategory())
            .when()
            .post("/api/categories")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .contentType(ContentType.JSON)
            .body(this.mockUpdatedCategory())
            .when()
            .put("/api/categories/" + categoryId)
            .then()
            .statusCode(200)
            .body("id", equalTo(categoryId))
            .body("name", equalTo("Geografia"));
    }

    @Test
    public void testUpdateCategoryNotFound() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockUpdatedCategory())
            .when()
            .put("/api/categories/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testDeleteCategory() {
        int categoryId = given()
            .contentType(ContentType.JSON)
            .body(this.mockCategory())
            .when()
            .post("/api/categories")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .when()
            .delete("/api/categories/" + categoryId)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/api/categories/" + categoryId)
            .then()
            .statusCode(404);
    }

    @Test
    public void testDeleteCategoryNotFound() {
        given()
            .when()
            .delete("/api/categories/9999")
            .then()
            .statusCode(404);
    }
}