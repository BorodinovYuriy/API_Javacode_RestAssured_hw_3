package org.ex.tests;

import com.ex.dtos.newuser.NewUserDTO;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.ex.api.PostReqApi;
import org.ex.config.PropertiesLoader;
import org.ex.tests.base.BaseApiJCTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Slf4j
public class ApiJCTests extends BaseApiJCTest {

    @ParameterizedTest(name = "Добавление user positive (Итер {index}), Arg: {arguments}")
    @MethodSource(value = "org.ex.data.DataProviders#excelPositive")
    @Execution(ExecutionMode.SAME_THREAD)
    public void addNewUserPositiveExel(NewUserDTO user) throws InterruptedException {
        Response response = PostReqApi.post(user, "/api/user-auth1", token);
        System.out.println(response.asString());
        Thread.sleep(100L);

        if(response.statusCode() == 200){
            mongo.deleteDocumentById(
                    PropertiesLoader.getMongoCollectionUsers(),
                    response.jsonPath().getInt("data._id")
            );
        }
        log.info(" // addNewUserPositiveExel test passed");
    }
    @ParameterizedTest(name = "Добавление user negative (Итер {index}), Arg: {arguments}")
    @MethodSource(value = "org.ex.data.DataProviders#excelNegative")
    public void addNewUserNegativeExel(NewUserDTO user){
        Response response = PostReqApi.post(user, "/api/user-auth1", token, 400);
        log.info(" // addNewUserNegativeExel test passed");
    }

    @Test()
    @DisplayName("Добавление нового интервью и его редактирование")
    public void addEditInterview(){
        String json = "{\"name\": \"TestingInterview\"}";
        Response response = PostReqApi.post(json, "/api/interview", token);

        String jsonSchemaPath = "jsonschema/interview.json";
        response.then()
            .body(matchesJsonSchemaInClasspath(jsonSchemaPath));

        int id = response.jsonPath().getInt("data._id");

        String jsonEdit = String.format(
                "{\"_id\": %d," +
                        " \"name\": \"RedactInterview\"," +
                        " \"user\": %d," +
                        " \"questions\": []," +
                        " \"cd\": \"\"," +
                        " \"sale\": \"1035\"," +
                        " \"problemQuestions\": \"0\"," +
                        " \"questionsSize\": \"0\"}",
                id,
                PropertiesLoader.getRegisterUserId()
        );

        Response respEdit = PostReqApi.put(jsonEdit, "/api/interview", token);
        respEdit.then()
                .body(matchesJsonSchemaInClasspath(jsonSchemaPath));

        Document document = mongo.getDocQueryInMongo(
                "interviews",
                id,
                "_id");

        Assertions.assertEquals(
                respEdit.jsonPath().getString("data.name"),
                document.get("name"));

        log.info(" // addRedactInterview test passed");
    }

    @Test
    public void test2(){}
    @Test
    public void test3(){}
    @Test
    public void test4(){}
    @Test
    public void test5(){}

}
