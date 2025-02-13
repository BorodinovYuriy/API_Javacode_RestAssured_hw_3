package org.ex.api;

import io.restassured.response.Response;
import org.ex.spec.ApiRequestSpecification;

import static io.restassured.RestAssured.given;


public class ReqApi {
    public static  <T> Response post(T data, String path) {
        return given()
                .spec(ApiRequestSpecification.getSpecUrlJson())
                .body(data)
                .when()
                .post(path)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static <T> Response post(T data, String path, String token) {
        return given()
                .spec(ApiRequestSpecification.getSpecUrlJson())
                .header("Authorization",token)
                .body(data)
                .when()
                .post(path)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
    public static <T> Response post(T data, String path,String token, int statusCode) {
        return given()
                .spec(ApiRequestSpecification.getSpecUrlJson())
                .header("Authorization",token)
                .body(data)
                .when()
                .post(path)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }
    public static <T> Response put(T data, String path, String token, int statusCode) {
        return given()
                .spec(ApiRequestSpecification.getSpecUrlJson())
                .header("Authorization",token)
                .body(data)
                .when()
                .put(path)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
