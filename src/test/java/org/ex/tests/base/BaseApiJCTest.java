package org.ex.tests.base;

import io.restassured.response.Response;
import org.ex.api.ReqApi;
import org.ex.config.PropertiesLoader;
import org.ex.helpers.MongoDB;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class BaseApiJCTest {

    protected String token;
    protected MongoDB mongo;

    @BeforeEach
    public void setup(){
        mongo = new MongoDB();

        Map<String,String> realU = new HashMap<>();

        realU.put("username", PropertiesLoader.getUsername());
        realU.put("password", PropertiesLoader.getPassword());

        Response response = ReqApi.post(
                realU,
                "/api/auth/login");

        token = response.jsonPath().getString("token");
    }

    @AfterAll
    private static void tearDown(){
        MongoDB.closeMongoClient();
    }

}
//   import org.junit.jupiter.api.BeforeEach;
//           import org.slf4j.Logger;
//           import org.slf4j.LoggerFactory;
//           import io.restassured.response.Response;
//
//           import java.util.HashMap;
//           import java.util.Map;
//
//public class BaseApiJCTest {
//
//    private static final Logger log = LoggerFactory.getLogger(BaseApiJCTest.class);
//
//    protected MongoDB mongo;
//    private static final ThreadLocal<String> token = new ThreadLocal<>();
//
//    @BeforeEach
//    public void setup() {
//        mongo = new MongoDB();
//
//        Map<String, String> realU = new HashMap<>();
//
//        realU.put("username", PropertiesLoader.getUsername());
//        realU.put("password", PropertiesLoader.getPassword());
//
//        Response response = ReqApi.post(
//                realU,
//                "/api/auth/login");
//
//        token.set(response.jsonPath().getString("token"));
//    }
//
//    protected String getToken() {
//        return token.get();
//    }
//
//    protected void clearToken() {
//        token.remove(); // Очищаем ThreadLocal после использования токена (опционально)
//    }
//
//    // Переопределите методы жизненного цикла, если необходимо
//    // Например, для очистки после всех тестов:
//    // @AfterAll
//    // static void cleanup() {
//    //     token.remove();
//    // }
//}