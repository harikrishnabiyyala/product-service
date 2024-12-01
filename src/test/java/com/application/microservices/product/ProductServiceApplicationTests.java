package com.application.microservices.product;


import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    private static final MongoDBContainer mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.5"));

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDbContainer.start();
    }

    @Test
    void shouldCreateProduct() {
        String requestBody = """
                {
                    "name" : "Android",
                    "description" : "This is an Android Phone",
                    "skuCode" : "ASJDHFJDSN",
                    "price" : 1000
                }
                """;

        var responseBodyString = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Android"))
                .body("description", Matchers.equalTo("This is an Android Phone"))
                .body("skuCode", Matchers.equalTo("ASJDHFJDSN"))
                .body("price", Matchers.equalTo(1000));


    }

}
