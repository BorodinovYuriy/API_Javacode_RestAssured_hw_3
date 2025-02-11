package org.ex.tests;

import com.ex.dtos.newuser.NewUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.bson.Document;
import org.ex.api.PostReqApi;
import org.ex.config.PropertiesLoader;
import org.ex.tests.base.BaseApiJCTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiJCTests extends BaseApiJCTest {
    private static final Logger logger = LoggerFactory.getLogger(ApiJCTests.class);

    @ParameterizedTest(name = "Добавление нового пользователя (Итер {index}), Arg: {arguments}")
    @MethodSource(value = "org.ex.data.DataProviders#excelDataProvider")
    public void addNewUser(NewUserDTO user){
        System.out.println("UserDTO: " + user);
        Response response = PostReqApi.post(user, "/api/user-auth1", token);
        System.out.println("STATUSCODE --- "+response.statusCode());

    mongo.deleteDocumentById(
            PropertiesLoader.getMongoCollectionUsers(),
            response.jsonPath().getInt("data._id")
    );
        logger.info("add new user - тест пройден.");
    }
    @Test

    public void test2(){}

}
