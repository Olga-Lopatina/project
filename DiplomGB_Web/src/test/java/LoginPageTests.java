import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTests extends AbstractTest {

    @BeforeEach
    void openLoginPage() {
        getDriver().get(urlLoginPage);
    }

    @DisplayName("Login exist user")
    @Order(1)
    @Test
    public void loginExistUser() {
        logIn(username, password);
        assertThat("Username should be " + username,
                getDriver().findElement(linkUser).getText(),
                is(equalTo("Hello, "+ username)));
    }

    @DisplayName("Login with invalid username and valid password")
    @Order(2)
    @Test
    public void loginInvalidUsernameValidPassword() {
        logIn("test", password);
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("Login with valid username and invalid password")
    @Order(3)
    @Test
    public void loginValidUsernameInvalidPassword() {
        logIn(username, "1234");
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("Login with empty username and password")
    @Order(4)
    @Test
    public void loginEmptyUsernameEmptyPassword() {
        logIn("", "");
        assertThat("Should be a message about empty fields",
                getDriver().findElement(label).getText(),
                is(equalTo("Поле не может быть пустым")));
    }


    @DisplayName("Login with 2 characters")
    @Order(7)
    @Test
    public void loginUsernameTwoCharacters() {
        logIn("be", password);
        assertThat("Should be a message about an invalid login length",
                getDriver().findElement(label).getText(),
                is(equalTo("Неправильный логин. Может быть не менее 3 и не более 20 символов")));
    }

    @DisplayName("Login with 3 characters")
    @Order(8)
    @Test
    public void loginUsernameThreeCharacters() {
        logIn("kjh", password);
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("Login with 20 characters")
    @Order(9)
    @Test
    public void loginUsername20Characters() {
        logIn("ghjklgfdcvbnjhgfdszx", password);
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("Login with 21 characters")
    @Order(10)
    @Test
    public void loginUsername21Characters() {
        logIn("ghjklgfdcvbnjhgfdszxr", password);
        assertThat("Should be a message about an invalid login length",
                getDriver().findElement(label).getText(),
                is(equalTo("Неправильный логин. Может быть не менее 3 и не более 20 символов")));
    }
}