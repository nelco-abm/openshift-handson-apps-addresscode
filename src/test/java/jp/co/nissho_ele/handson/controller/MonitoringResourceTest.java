package jp.co.nissho_ele.handson.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MonitoringResourceTest {

    @Test
    public void testProbeEndpoint() {
        given().when().get("/probe/distruct/false").then().statusCode(200);
        given().when().get("/probe").then().statusCode(200).body(is("success"));
    }

    @Test
    public void testDistructEndpoint() {
        given().when().get("/probe/distruct/true").then().statusCode(200);
        given().when().get("/probe").then().statusCode(503).body(is("error"));
    }

}