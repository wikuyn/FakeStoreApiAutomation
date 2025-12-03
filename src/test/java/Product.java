import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;


public class Product {

    public String title, description, imageUrl;
    public int productId, count;
    public float rate;

    @Test
    public void getProductList() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = RestAssured
                .given()
                .when()
                .get("/products");


        response.then()
                .body("size()", equalTo(20))
                .assertThat().statusCode(200);

        title = response
                .jsonPath().getString("[0]");


        System.out.println(title);
    }


    @Test
    public void getSpecifictProduct(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = RestAssured
                .given()
                .pathParam("id", 3)
                .when()
                .get("/products/{id}");

        System.out.println(response.print());

        response.then()
                .body("id", equalTo(3))
                .statusCode(200);
    }

}
