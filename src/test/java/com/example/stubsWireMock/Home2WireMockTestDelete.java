package com.example.stubsWireMock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;

@SpringBootTest
class Home2WireMockTestDelete {

    private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

    @BeforeAll
    public static void setUpServer(){
        wireMockServer.start();

        WireMock.configureFor("localhost", 5050);
        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo("/api/users/2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(204)
                        ));



    }

    @Test
    void contextLoads() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
//				.delete("https://reqres.in/api/users/2")
                .delete("http://localhost:5050/api/users/2")
                .then()
                .extract().response();

        Assertions.assertEquals(204, response.statusCode());
        System.out.println(response.getBody().prettyPrint());
           }

    @AfterAll
    public static void tearDownMockServer(){
        wireMockServer.stop();
    }

}
