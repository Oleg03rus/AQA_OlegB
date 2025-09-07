package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";
    private final static String API_KEY = "x-api-key";
    private final static String API_VALUE = "reqres-free-v1";


    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        List<UserData> users = given()
                .header(API_KEY, API_VALUE)
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> id = users.stream().map(x -> x.getId().toString()).toList();

        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(id.get(i)));
        }
    }

    @Test
    public void successRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");

        SuccessReg successReg = given()
                .header(API_KEY, API_VALUE)
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assertions.assertNotNull(successReg.getId());
        Assertions.assertNotNull(successReg.getToken());
        Assertions.assertEquals(id, successReg.getId());
        Assertions.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(400));
        String err = "Missing password";
        Register user = new Register("sydney@fife", "");
        UnSuccessReg unSuccessReg = given()
                .header(API_KEY, API_VALUE)
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assertions.assertEquals("Missing password", unSuccessReg.getError());
    }

    @Test
    public void sortedYearsTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        List<YearsData> colors = given()
                .header(API_KEY, API_VALUE)
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", YearsData.class);
        List<Integer> years = colors.stream()
                .map(YearsData::getYear)
                .toList();
        List<Integer> sortedYears = years.stream()
                .sorted()
                .toList();
        Assertions.assertEquals(sortedYears, years);
        System.out.println(years);
        System.out.println(sortedYears);
    }

    @Test
    public void deleteUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .header(API_KEY, API_VALUE)
                .when()
                .delete("api/users/2")
                .then().log().all();
    }

    @Test
    public void timeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        UserTime userTime = new UserTime("morpheus", "zion resident");
        UserTimeResponse userTimeResponse = given()
                .header(API_KEY, API_VALUE)
                .body(userTime)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);
        String regex = "(.{5})$";
        String regex2 = "(.{11})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex2, "");
        System.out.println(currentTime);

        Assertions.assertEquals(currentTime, userTimeResponse.getUpdatedAt().replaceAll(regex, ""));

        System.out.println((userTimeResponse.getUpdatedAt().replaceAll(regex, "")));
    }
}
