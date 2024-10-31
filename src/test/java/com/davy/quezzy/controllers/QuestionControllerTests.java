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
public class QuestionControllerTests {
    
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
    @Sql(statements = "DELETE FROM questions")
    @Transactional
    public void clearDatabase() {}

    private String mockQuestion() {
        return "{ \"question\": \"What is the capital of France?\", \"answer\": \"Paris\", \"categoryId\": 1, \"creatorId\": 1, \"difficulty\": \"easy\", \"wrongAnswer1\": \"London\", \"wrongAnswer2\": \"Berlin\", \"wrongAnswer3\": \"Madrid\" }";
    }

    private String mockUpdatedQuestion() {
        return "{ \"question\": \"What is the capital of Germany?\", \"answer\": \"Berlin\", \"categoryId\": 1, \"creatorId\": 1, \"difficulty\": \"medium\", \"wrongAnswer1\": \"Paris\", \"wrongAnswer2\": \"London\", \"wrongAnswer3\": \"Madrid\" }";
    }

    @Test
    public void testListAllQuestions() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockQuestion())
            .when()
            .post("/api/questions")
            .then()
            .statusCode(201);
    
        given()
            .when()
            .get("/api/questions")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("[0].question", notNullValue())
            .body("[0].answer", notNullValue());
    }

    @Test
    public void testReturnEmptyWhenNoQuestions() {
        given()
            .when()
            .get("/api/questions")
            .then()
            .statusCode(200)
            .body("size()", equalTo(0));
    }

    @Test
    public void testCreateQuestion() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockQuestion())
            .when()
            .post("/api/questions")
            .then()
            .statusCode(201)
            .body("question", equalTo("What is the capital of France?"))
            .body("answer", equalTo("Paris"));
    }

    @Test
    public void testGetQuestionById() {
        int questionId = given()
            .contentType(ContentType.JSON)
            .body(this.mockQuestion())
            .when()
            .post("/api/questions")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .when()
            .get("/api/questions/" + questionId)
            .then()
            .statusCode(200)
            .body("id", equalTo(questionId))
            .body("question", equalTo("What is the capital of France?"))
            .body("answer", equalTo("Paris"));
    }

    @Test
    public void testGetQuestionByIdNotFound() {
        given()
            .when()
            .get("/api/questions/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetQuestionsByCategory() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockQuestion())
            .when()
            .post("/api/questions")
            .then()
            .statusCode(201);

        given()
            .when()
            .get("/api/questions/category/1")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    public void testUpdateQuestion() {
        int questionId = given()
            .contentType(ContentType.JSON)
            .body(this.mockQuestion())
            .when()
            .post("/api/questions")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .contentType(ContentType.JSON)
            .body(this.mockUpdatedQuestion())
            .when()
            .put("/api/questions/" + questionId)
            .then()
            .statusCode(200)
            .body("id", equalTo(questionId))
            .body("question", equalTo("What is the capital of Germany?"))
            .body("answer", equalTo("Berlin"));
    }

    @Test
    public void testUpdateQuestionNotFound() {
        given()
            .contentType(ContentType.JSON)
            .body(this.mockUpdatedQuestion())
            .when()
            .put("/api/questions/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testDeleteQuestion() {
        int questionId = given()
            .contentType(ContentType.JSON)
            .body(this.mockQuestion())
            .when()
            .post("/api/questions")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

        given()
            .when()
            .delete("/api/questions/" + questionId)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/api/questions/" + questionId)
            .then()
            .statusCode(404);
    }

    @Test
    public void testDeleteQuestionNotFound() {
        given()
            .when()
            .delete("/api/questions/9999")
            .then()
            .statusCode(404);
    }
}