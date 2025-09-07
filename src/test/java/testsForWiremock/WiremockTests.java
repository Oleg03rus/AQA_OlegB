package testsForWiremock;

import api.Specifications;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class WiremockTests extends Specifications {
    private final static String URL = "http://localhost:8080";

    @Test
    public void getProfile() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        given().when()
                .get("/api/profile")
                .then().log().all()
                .body("name", equalTo("Oleg"))
                .body("age", equalTo(34));
    }

    @Test
    public void postProfile() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(201));
        given().when()
                .post("/api/profile")
                .then().log().all()
                .body("id", notNullValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {1234, 321, 777, 0245})
    public void notFoundProfile(int userId) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(404));
        given().when()
                .get("/api/profile" + userId)
                .then().log().all();
    }
}
