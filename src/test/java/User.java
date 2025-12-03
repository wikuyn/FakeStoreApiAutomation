import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class User {


    @Test
    public void getSpesifictUser(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = RestAssured.given()
                .pathParam("id", 1)
                .get("/users/{id}");

        System.out.println(response.jsonPath().getString("username"));

        response.then()
                .statusCode(200)
                .body("address.number",equalTo(7682));

    }
}
