import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestTests1 {


    RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setBasePath("/rest_service/api/v1/")
            .setPort(8189)
            .build();

    @ParameterizedTest(name = "{index} => a={0}, b={1}, mult={2}")  // Тест для второго задания ДЗ
    @CsvSource({
            "5, 3, 15",
            "14, 3, 42",
            "6, 6, 36",
            "36, 2,  72",
            "16, 8, 128"
    })

    public void testWithParametrize(int a, int b, String mult) {
        given()
                .param("a", a)
                .param("b", b)
                .spec(baseSpec)
        .when()
                .get("/math1")
        .then()
                .statusCode(200)
                .body(notNullValue(), equalTo(mult));
    }


    @Test
    public void testPutItem() { // тест Put для 3-го задания ДЗ
        given()
                .contentType(ContentType.JSON)
                .spec(baseSpec)
                .body("{\"id\": \"3\",\r\n" + "\"title\": \"Tier\"}") // передача JSON в тело
                .put("/items")
                .then()
                .statusCode(200)
                .body("title", equalTo("Tier"));
    }


}
