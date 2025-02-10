package org.ex.tests.base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.ex.api.PostReqApi;
import org.ex.config.PropertiesLoader;
import org.ex.helpers.MongoDB;
import org.ex.spec.ApiRequestSpecification;
import org.ex.tests.ApiJCTests;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BaseApiJCTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiJCTest.class);

    private String token;
    protected MongoDB mongo;

    @BeforeEach
    public void setup(){
        mongo = new MongoDB();
        Map<String,String> realU = new HashMap<>();
        realU.put("username",PropertiesLoader.getUsername());
        realU.put("password",PropertiesLoader.getPassword());


        Response response = PostReqApi.post(realU,
                "/api/auth/login");

        Assertions.assertTrue(response.getContentType().contains(ContentType.JSON.toString()),
                "Content-Type должен содержать application/json");

        token = response.jsonPath().getString("token");
        logger.info("Новый token получен: {}", token);
    }

    @AfterAll
    private static void tearDown(){
        MongoDB.closeMongoClient();
    }

}
