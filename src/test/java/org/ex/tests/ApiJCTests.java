package org.ex.tests;

import com.ex.dtos.newuser.NewUserDTO;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.ex.api.PostReqApi;
import org.ex.config.PropertiesLoader;
import org.ex.tests.base.BaseApiJCTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @Test
    public void test1(){}

    @Test
    public void test2(){}
    @Test
    public void test3(){}
    @Test
    public void test4(){}
    @Test
    public void test5(){}

}
