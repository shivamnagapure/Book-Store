package com.shivam.catalogservice;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class AbstractIT {
    @LocalServerPort
    int port;

    // to execute Api call by RestAssured it needs -> baseURI + port + path
    @BeforeEach
    void setPort() {
        System.out.println("IN Test");
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        ;
    }
}
