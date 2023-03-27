import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends AbstractTest {

    @DisplayName("Valid login and password")
    @Order(1)
    @Test
    void loginValidUsernameValidPassword() {
        JsonPath jsonPath = given()
                .contentType("multipart/form-data")
                .multiPart("username", getUsername())
                .multiPart("password", getPassword())
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().jsonPath();
        assertThat(jsonPath.get("username"), equalTo(getUsername()));
    }

    @DisplayName("Valid login and invalid password")
    @Test
    @Order(2)
    void loginValidUsernameInvalidPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", getUsername())
                .multiPart("password", "1234")
                .post(getBaseUrl() + "/gateway/login")
                .then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("Invalid credentials."));
    }

    @DisplayName("Invalid login and valid password")
    @Order(3)
    @Test
    void loginInvalidUsernameValidPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "usernotexist")
                .multiPart("password", getPassword())
                .post(getBaseUrl() + "/gateway/login")
                .then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("Invalid credentials."));
    }

    @DisplayName("Invalid login and password")
    @Order(4)
    @Test
    void loginInvalidUsernameInvalidPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "usernotexist")
                .multiPart("password", "1234")
                .post(getBaseUrl() + "/gateway/login")
                .then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("Invalid credentials."));
    }

    @DisplayName("Empty login and password")
    @Order(5)
    @Test
    void loginEmptyUsernameEmptyPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", "")
                .post(getBaseUrl() + "/gateway/login")
                .then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("Invalid credentials."));
    }

    @DisplayName("Empty login and valid password")
    @Order(6)
    @Test
    void loginEmptyUsernameValidPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", getPassword())
                .post(getBaseUrl() + "/gateway/login")
                .then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("Invalid credentials."));
    }



}
