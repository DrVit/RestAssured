import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestTests {


    RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/rest_service/api/v1/")
            .setPort(8189)
            .build();

    @Test
    public void testItemTitle() {

        when()
                .get("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .body("[0].title", equalTo("Box"));
    }

    @Test
    public void testGetItemsResponseCode() { //проверка на статус-код

        when()
                .get("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .statusCode(200);
    }

    @Test
    public void testWithPathParam() {
        given()
                .pathParam("id", "1")
                .when()
                .get("http://localhost:8189/rest_service/api/v1/items/{id}")
                .then()
                //.statusCode(200);  // для проверки статус-кода
                // .extract().response().body().print(); // для вывода содержимого
                .body("title", equalTo("Square"));
    }


    @Test
    public void testWithParams() {
        given()
                .param("a", "1")
                .param("b", "2")
                .when()
                .get("http://localhost:8189/rest_service/api/v1/math")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUserWithParam() { // тест на Get
        given()
                .pathParam("id", "1")
                .when()
                .get("http://localhost:8189/rest_service/api/v1/clients/{id}")
                .then()
                .body("items[0].title", equalTo("Box"));
    }

    @Test
    public void testAddItem() { // тест postman
        given()
                .contentType(ContentType.JSON) // чтобы не прописывать .headers
                .accept(ContentType.JSON)
//                .header("content-type", "application/json")
//                .header("accept", "application/json")
                .body("{\"title\": \"Toy\"}") // передача JSON в тело
                .post("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .body("title", equalTo("Toy"));
    }

    @Test
    public void testAddItemFromObject() { // тест создания из объекта
        Item item = new Item();
        item.setTitle("JavaObject");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(item)
                .post("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .body("id", notNullValue());
    }

    @Test
    public void testWithRequestSpec() { // тест спецификации  (baseSpec - задает параметпы пути и порта.)
        given()
                .spec(baseSpec)
                .get("/items")
                .then()
                .extract().response().body().print();
    }

    @Test
    public void testPutItem() { // тест Put для 3-го задания ДЗ
        given()
                .contentType(ContentType.JSON)
                .body("{\"id\": \"3\",\r\n" + "\"title\": \"Tier\"}") // передача JSON в тело
                .put("http://localhost:8189/rest_service/api/v1/items")
                .then()
                .statusCode(200)
                .body("title", equalTo("Tier"));
    }


}
