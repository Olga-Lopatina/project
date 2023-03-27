import io.restassured.path.json.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OtherPostsTest extends AbstractTest {

    @DisplayName("Others posts")
    @Order(1)
    @Test
    void getOtherPosts() {
        given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("owner", "notMe")
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
    @DisplayName("Others posts sorted default")
    @Order(2)
    @Test
    void getOtherPostsSortedCreatedAt() {
        JsonPath jsonPath = given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("owner", "notMe")
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
    @DisplayName("Others posts sorted ASC")
    @Order(3)
    @Test
    void getOtherPostsSortedASC() {
        JsonPath jsonPath = given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("owner", "notMe")
                .queryParam("order", "ASC")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().jsonPath();
        String date1 = jsonPath.get("data[0].createdAt");
        String date2 = jsonPath.get("data[1].createdAt");
        assertTrue(simpleDateFormat.parse(date1).getTime() < simpleDateFormat.parse(date2).getTime(), "TСортировка должна быть по возрастанию");
    }

    @SneakyThrows
    @DisplayName("Others posts sorted DESC")
    @Order(4)
    @Test
    void getOtherPostsSortedDESC() {
        JsonPath jsonPath = given()
                .header("X-Auth-Token", getXAuthToken())
                .queryParam("owner", "notMe")
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


    @DisplayName("Others posts without token")
    @Order(9)
    @Test
    void getOtherPostWithoutToken() {
        given()
                .queryParam("owner", "notMe")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .statusCode(401);
    }

    @DisplayName("Others posts with invalid token")
    @Order(10)
    @Test
    void getOtherPostsWithInvalidToken() {
        given()
                .header("X-Auth-Token", "a9c66f5c65741c9dffced2c5da03da")
                .queryParam("owner", "notMe")
                .get(getBaseUrl() + "/api/posts")
                .then()
                .statusCode(401);
    }


}
