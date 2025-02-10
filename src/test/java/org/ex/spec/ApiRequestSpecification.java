package org.ex.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.ex.config.PropertiesLoader;

public class ApiRequestSpecification {
    public static RequestSpecification getSpecUrlJson(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(PropertiesLoader.getBaseURI());
        requestSpecBuilder.setContentType(ContentType.JSON);
        return requestSpecBuilder.build();
    }
}
