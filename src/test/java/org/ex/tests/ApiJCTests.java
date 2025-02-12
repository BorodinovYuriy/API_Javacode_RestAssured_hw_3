package org.ex.tests;

import com.ex.dtos.newuser.NewUserDTO;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.ex.api.PostReqApi;
import org.ex.config.PropertiesLoader;
import org.ex.helpers.JSONHelper;
import org.ex.tests.base.BaseApiJCTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Paths;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class ApiJCTests extends BaseApiJCTest {

    @ParameterizedTest(name = "Добавление user positive ( - {index}), Arg: {arguments}")
    @MethodSource(value = "org.ex.data.DataProviders#excelPositive")
    @Execution(ExecutionMode.SAME_THREAD)
    public void addNewUserPositiveExel(NewUserDTO user) {
        Response response = PostReqApi.post(user, "/api/user-auth1", token);
            mongo.deleteDocumentById(
                    PropertiesLoader.getMongoCollectionUsers(),
                    response.jsonPath().getInt("data._id")
            );

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
        Response response = PostReqApi.post(
                "{\"name\": \"TestingInterview\"}",
                "/api/interview", token);
        response.then()
            .body(matchesJsonSchemaInClasspath("jsonschema/interview.json"));

        int id = response.jsonPath().getInt("data._id");

        String jsonEdit = String.format(
                "{\"_id\": %d," +
                        " \"name\": \"RedactInterview\"," +
                        " \"user\": %d," +
                        " \"questions\": []," +
                        " \"cd\": \"\"," +
                        " \"sale\": \"%d\"," +
                        " \"problemQuestions\": \"0\"," +
                        " \"questionsSize\": \"0\"}",
                id,
                PropertiesLoader.getRegisterUserId(),
                id
        );

        Response respEdit = PostReqApi.put(jsonEdit, "/api/interview", token, 200);
        respEdit.then()
                .body(matchesJsonSchemaInClasspath("jsonschema/interview.json"));

        Document document = mongo.getDocQueryInMongo(
                "interviews",
                id,
                "_id");

        Assertions.assertEquals(
                respEdit.jsonPath().getString("data.name"),
                document.get("name"));

        log.info(" // addEditInterview test passed");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "noValidData.csv")
    @DisplayName("Создание/изменение экзамена - не валидные данные")
    public void noValidExamEditData(String date){
        Response response = PostReqApi.post(
          JSONHelper.fileToJSON(
                  Paths.get("src/test/resources/json/addExam.json")), "/api/exam", token);

        PostReqApi.put(
                String.format("{\"cd\": \"%s\", \"_id\": %d}",
                date,
                response.jsonPath().getInt("data._id")),
                "/api/exam",
                token,
                400)
                .then()
                .assertThat()
                .body("_message", equalTo("Exam validation failed"));

        log.info(" // noValidExamEdit test passed");
    }
    @ParameterizedTest
    @CsvFileSource(resources = "noValidData.csv")
    @DisplayName("Создание/изменение квиза - не валидные данные")
    public void noValidQuizEditData(String date){
        Response response = PostReqApi.post(
                JSONHelper.fileToJSON(
                        Paths.get("src/test/resources/json/addQuiz.json")), "/api/quiz", token);

        PostReqApi.put(
                        String.format("{\"cd\": \"%s\", \"_id\": %d}",
                                date,
                                response.jsonPath().getInt("data._id")),
                                "/api/quiz",
                                token,
                                400)
                        .then()
                        .assertThat()
                        .body("_message", equalTo("Quiz validation failed"));

        log.info(" // noValidQuizEdit test passed");
    }
    @ParameterizedTest(name = "Добавление уже существующего пользователя ( - {index}), Arg: {arguments}")
    @MethodSource(value = "org.ex.data.DataProviders#excelPositive")
    @Execution(ExecutionMode.SAME_THREAD)
    public void addExistUser(NewUserDTO user){
        Response response = PostReqApi.post(user, "/api/user-auth1", token);

        PostReqApi.post(user, "/api/user-auth1", token,400);

        mongo.deleteDocumentById(
                PropertiesLoader.getMongoCollectionUsers(),
                response.jsonPath().getInt("data._id")
        );

        log.info(" // addExistUser test passed");
    }
    @Test
    public void test(){
        Assertions.assertEquals(1,2);
    }














}
