package com.example.stubsWireMock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static io.restassured.RestAssured.given;

@SpringBootTest
class Home3WireMockTestPostCreate {

    private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050));

    @BeforeAll
    public static void setUpServer(){
        wireMockServer.start();


        WireMock.configureFor("localhost", 5050);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users"))
                        .withRequestBody(equalToJson("{\n" +
                                "    \"name\": \"morpheus\",\n" +
                                "    \"job\": \"leader\"\n" +
                                "}"))
                        .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withBody("{\n" +
                                "    \"name\": \"morpheus\",\n" +
                                "    \"job\": \"leader\",\n" +
                                "    \"id\": \"678\",\n" +
                                "    \"createdAt\": \"2021-07-05T18:01:32.235Z\"\n" +
                                "}")));


    }

    private static MappingBuilder aMultipart() {
        return null;
    }

    @Test
    void contextLoads() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
				.post("https://reqres.in/api/users")
//                .post("http://localhost:5050/api/users")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        System.out.println(response.getBody().prettyPrint());
        Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
//        Assertions.assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", response.jsonPath().getString("support.text"));
    }

    @AfterAll
    public static void tearDownMockServer(){
        wireMockServer.stop();
    }

}
