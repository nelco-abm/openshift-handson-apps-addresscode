package jp.co.nissho_ele.handson.controller;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AddressControllerTest {

    @Test
    public void testPostalCode() {
        String code = "1100001";
        given().pathParam("code", code).when().get("/address/postalcode/{code}").then().statusCode(200).body("[0]",
                Matchers.equalToObject("東京都台東区谷中"));
    }
}