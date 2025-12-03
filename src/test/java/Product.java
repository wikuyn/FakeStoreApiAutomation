import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;

import static org.hamcrest.Matchers.*;


public class Product {

    public final String URL = "https://api-staging.lp3es-prisma.co.id/v1";
    public String title, description, imageUrl;
    public int productId, count;
    public float rate;

    @Test
    public void getProductList() {
        RestAssured.baseURI = URL;
        Response response = RestAssured
                .given()
                .header("device_type", "WEB")
                .header("Language", "id")
                .header("version_code", "1")
                .header("version_name", "1.0.0")
                .when()
                .get("/product");


        /*
        response.then()
                .body("size()", equalTo(20))
                .assertThat().statusCode(200);

        title = response
                .jsonPath().getString("[0]");

         */

        title = response
                .jsonPath().getString("data[0].product_id");
        response.then()
                        .statusCode(200);


        System.out.println(title);
    }


    @Test
    public void getSpecifictProduct() throws SQLException {
        Connection connectDatabase;
        Statement statement;
        RestAssured.baseURI = URL;
        Response response = RestAssured
                .given()
                .header("device_type", "WEB")
                .header("Language", "id")
                .header("version_code", "1")
                .header("version_name", "1.0.0")
                .pathParam("id", 3)
                .when()
                .get("/product/{id}");

        System.out.println(response.print());


        //validating the status code and response data
        response.then()
                .body("data.product_id", equalTo(3))
                .statusCode(200);

        //validating to the database but still using mock database
        connectDatabase = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_bussid",
                "root",
                ""
        );
        statement = connectDatabase.createStatement();
        String query = "select * from `mod` p where p.id  = 1";
        ResultSet set = statement.executeQuery(query);

        Assert.assertTrue(set.next(), "Data tidak ditemukan!");

        Assert.assertEquals(set.getInt("id"), 1);

        String name = set.getString("name");
        System.out.println(name);
    }

    @Test
    public void getSpesifctProductNotFound(){
        RestAssured.baseURI = URL;
        Response response = RestAssured
                .given()
                .header("device_type", "WEB")
                .header("Language", "id")
                .header("version_code", "1")
                .header("version_name", "1.0.0")
                .pathParam("id", 999)
                .when()
                .get("/product/{id}");

        System.out.println(response.print());
        response.then()
                .statusCode(404)
                .body("meta.msg", equalTo("Not Found"));
    }




}
