package apiTestExamples;

import api.Specifications;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests extends Specifications {
    private final static String URL_REQRES = "https://reqres.in/";
    private final static String URL = "https://jsonplaceholder.typicode.com/";
    private final static String URL_PetStore = "https://petstore.swagger.io/v2/";
    private final static String API_KEY = "x-api-key";
    private final static String API_VALUE = "reqres-free-v1";

    //Задание 1: Простые GET-запросы
    @Test
    public void firstTaskTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        Response response = given()
                .when()
                .get("posts")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> posts = jsonPath.get("id");
        List<Integer> userId = jsonPath.get("userId");
        Assertions.assertEquals(100, posts.size());
        Assertions.assertEquals(userId.get(1), 1);
    }

    //Задание 2: Работа с параметрами и валидацией JSON
    @Test
    public void secondTaskTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL));
        Response response = given()
                .when()
                .get("posts?userId=1")
                .then().log().all()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SchemaJson.json"))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> userId = jsonPath.get("userId");
        List<Integer> id = jsonPath.get("id");
        List<Integer> idSorted = id.stream().sorted().toList();
        Assertions.assertTrue(userId.stream().allMatch(x -> x == 1));
        Assertions.assertEquals(id, idSorted);
    }

    //Задание 3: POST/PUT/DELETE-запросы
    @Test
    public void thirdTaskPostRequestTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(201));
        Map<String, String> user = new HashMap<>();
        user.put("name", "Oleg");
        user.put("job", "AQA");
        Response response = given()
                .body(user)
                .when()
                .post("posts")
                .then().log().all()
                .body("id", notNullValue())
                .extract().response();
    }

    //Задание 3: POST/PUT/DELETE-запросы
    @Test
    public void thirdTaskPutRequestTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        Map<String, String> user = new HashMap<>();
        user.put("name", "Oleg2");
        user.put("job", "AQA-2");
        given()
                .body(user)
                .when()
                .put("users/2")
                .then().log().all()
                .body("name", equalTo("Oleg2"))
                .body("job", equalTo("AQA-2"));
    }

    //Задание 3: POST/PUT/DELETE-запросы
    //В этом задании, после отправки запроса, статус код = 200, если делать проверку на 204, то тест падает. Может устарела информация?
    @Test
    public void thirdTaskDeleteRequestTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        given()
                .when()
                .delete("users/2")
                .then().log().all();
    }

    //Задание 4: Авторизация и работа с токенами, REGEX
    @Test
    public void fourthTaskCreateUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL_PetStore), Specifications.responseSpecUnique(200));
        Map<String, Object> user = new HashMap<>();
        user.put("id", 444);
        user.put("username", "Oleg");
        user.put("firstname", "Oleg");
        user.put("lastName", "B");
        user.put("email", "OlegB@gmail.com");
        user.put("password", "123456");
        user.put("phone", "123456789");
        user.put("userStatus", 0);
        given()
                .when()
                .body(user)
                .post("user")
                .then().log().all()
                .body("message", equalTo("444"));
    }

    //Задание 4: Авторизация и работа с токенами, REGEX
    @Test
    public void fourthTaskLoginUserTask() {
        Specifications.installSpecification(Specifications.requestSpec(URL_PetStore), Specifications.responseSpecUnique(200));
        String username = "Oleg";
        String password = "123456";
        given()
                .when()
                .body(username + password)
                .get("user/login")
                .then().log().all()
                .body("message", matchesPattern("logged in user session:\\d+"));
    }

    //Задание 4: Авторизация и работа с токенами, REGEX
    @Test
    public void fourthTaskGetUserTask() {
        Specifications.installSpecification(Specifications.requestSpec(URL_PetStore), Specifications.responseSpecUnique(200));
        String username = "Oleg";
        Response response = given()
                .when()
                .body(username)
                .get("user/" + username)
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        String responseUsername = jsonPath.get("username");
        String responseEmail = jsonPath.get("email");
        String responsePhone = jsonPath.get("phone");
        Assertions.assertEquals("Oleg", responseUsername);
        Assertions.assertEquals("OlegB@gmail.com", responseEmail);
        Assertions.assertEquals("123456789", responsePhone);
    }

    //Задание 5: Негативные тесты и ошибки
    @Test
    public void fifthTaskNegativeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL_REQRES), Specifications.responseSpecUnique(400));
        Map<String, String> user = new HashMap<>();
        user.put("email", "peter@klaven");
        given()
                .header(API_KEY, API_VALUE)
                .body(user)
                .when()
                .post("api/login")
                .then().log().all()
                .body("error", anyOf(equalTo("User not found"), equalTo("Missing password")));
    }

    //Задание 5: Негативные тесты и ошибки
    @Test
    public void fifthTask2NegativeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL_REQRES), Specifications.responseSpecUnique(404));
        given()
                .header(API_KEY, API_VALUE)
                .when()
                .get("api/users/999")
                .then().log().all()
                .body(equalTo("{}"));
    }
}
