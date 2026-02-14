package apiTestExamples;

import api.Specifications;
import groovyjarjarpicocli.CommandLine;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EndToEndTest extends Specifications {
    private static final String URL = "https://reqres.in/";
    private final static String API_KEY = "x-api-key";
    private final static String API_VALUE = "reqres-free-v1";

    @Test
    public void endToEndTest() {
        String userEmail = "eve.holt@reqres.in";
        String userPassword = "pistol";
        String name = "Oleg";
        String job = "AQA";
        String updateJob = "AQA-JAVA";

        registerUser(userEmail, userPassword);
        loginUser(userEmail, userPassword);
        createUser(name, job);
        updateUser(name, updateJob);
        deleteUser();

    }

    public void registerUser(String userEmail, String userPassword) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        given()
                .header(API_KEY, API_VALUE)
                .body("{\"email\": \"" + userEmail + "\", \"password\": \"" + userPassword + "\"}")
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    public void loginUser(String userEmail, String userPassword) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        given()
                .header(API_KEY, API_VALUE)
                .body("{\"email\": \"" + userEmail + "\", \"password\": \"" + userPassword + "\"}")
                .when()
                .post("api/login")
                .then().log().all()
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    public void createUser(String name, String job) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(201));
        given()
                .header(API_KEY, API_VALUE)
                .body("{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}")
                .when()
                .post("api/create")
                .then().log().all()
                .body("name", equalTo("Oleg"))
                .body("job", equalTo("AQA"));
    }

    public void updateUser(String name, String job) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        given()
                .header(API_KEY, API_VALUE)
                .when()
                .body("{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}")
                .when()
                .put("api/users/2")
                .then().log().all()
                .body("name", equalTo("Oleg"))
                .body("job", equalTo("AQA-JAVA"));
    }

    public void deleteUser() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .header(API_KEY, API_VALUE)
                .when()
                .delete("api/users/2")
                .then().log().all();
    }
}
