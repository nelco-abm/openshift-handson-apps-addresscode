package jp.co.nissho_ele.handson.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class MonitoringResourceTest {

    @Test
    public void testHelloEndpoint() {
        given().when().get("/probe").then().statusCode(200).body(is("success"));
    }

}