import io.restassured.path.json.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyPostsTest extends AbstractTest {

    @DisplayName("My posts without query params")
    @Order(1)
    @Test
    void getMyPostsWithoutQueryParams() {
        given()
                .header("X-Auth-Token", getXAuthToken())
                .get(getBaseUrl() + "/api/posts")
                .then()
                .statusCode(200)
                //.log().all()
                .assertThat()
                .body("meta.nextPage", equalTo(2))
                .body("meta.prevPage", equalTo(1))
                .body("data.size()", equalTo(4));
    }

    @SneakyThrows
    @DisplayName("My posts sorted default")
    @Order(2)
    @Test
    void getMyPostsSortedCreatedAt() {
        JsonPath jsonPath = given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("sort", "createdAt")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().jsonPath();
        String date1 = jsonPath.get("data[0].createdAt");
        String date2 = jsonPath.get("data[1].createdAt");
        assertTrue(simpleDateFormat.parse(date1).getTime() < simpleDateFormat.parse(date2).getTime(), "Сортировка по умолчанию должна быть ASC (от старых к новым)");
    }

    @SneakyThrows
    @DisplayName("My posts sorted ASC")
    @Order(3)
    @Test
    void getMyPostsSortedASC() {
        JsonPath jsonPath = given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().jsonPath();
        String date1 = jsonPath.get("data[0].createdAt");
        String date2 = jsonPath.get("data[1].createdAt");
        assertTrue(simpleDateFormat.parse(date1).getTime() < simpleDateFormat.parse(date2).getTime(), "Сортировка должна быть по возрастанию");
    }

    @SneakyThrows
    @DisplayName("My posts sorted DESC")
    @Order(4)
    @Test
    void getMyPostsSortedDESC() {
        JsonPath jsonPath = given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().jsonPath();
        String date1 = jsonPath.get("data[0].createdAt");
        String date2 = jsonPath.get("data[1].createdAt");
        assertTrue(simpleDateFormat.parse(date1).getTime() > simpleDateFormat.parse(date2).getTime(), "Сортировка должна быть по убыванию");
    }


    @DisplayName("My posts without token")
    @Order(9)
    @Test
    void getMyPostsWithoutToken() {
        given()
                //.header("X-Auth-Token", getXAuthToken())
                .get(getBaseUrl() + "/api/posts")
                .then()
                .statusCode(401);
    }

    @DisplayName("My posts with invalid token")
    @Order(10)
    @Test
    void getMyPostsWithInvalidToken() {
        given()
                .header("X-Auth-Token", "a9c66f5c65741c9dffced2c5da03da")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .statusCode(401);
    }


}
