package org.ex.tests.base;

import org.ex.api.PostReqApi;
import org.ex.helpers.MongoDB;
import org.junit.jupiter.api.BeforeAll;

public class BaseJCTest {
    String token;
    String reqSpec;
    MongoDB mongo;
    PostReqApi api;

    @BeforeAll
    public static void setup(){

    }
}
