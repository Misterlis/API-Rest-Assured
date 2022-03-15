import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Написать тесты с использованием Json, проверить валидность данных в ответе на запрос
 * На сайте есть документация по АPI
 * https://reqres.in/ *
 * Создать в проекте файл с расширением .Json
 * { "name": "Potato" }
 * Создать тест запрос для создания пользователя (CREATE)
 * Body в запрос передать из ранее созданного файла, поменяв name и добавив поле Job
 * { "name": "Tomato", "job": "Eat maket" }
 * Проверить, что получили ответ 200.
 * Свериться, что полученный response имеет валидные данные по значениям key и value.
 * {
 * "name": "Tomato",
 * "job": "Eat maket",
 * "id": "325",
 * "createdAt": "2021-08-03T10:22:44.071Z"
 * }
 */
public class ApiTest2 {

    @Tag("ApiTest2")
    @Test
    @DisplayName("ApiTest2")
    public void test1() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Tomato");
        requestBody.put("job", "Eat maket");

        Response response3 = given()
                .baseUri("https://reqres.in/")
                .contentType("application/json;charset=UTF-8")
                .log().all()
                .when()
                .body(requestBody.toString())
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().all()
                .extract().response();

        String jsonname = new JSONObject(response3.body().asString()).getString("name");
        String jsonjob = new JSONObject(response3.body().asString()).getString("job");
        Assertions.assertEquals(jsonname, "Tomato");
        Assertions.assertEquals(jsonjob, "Eat maket");
    }
}

