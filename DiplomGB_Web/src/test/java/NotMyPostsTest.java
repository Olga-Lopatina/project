import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotMyPostsTest extends AbstractTest {

    @DisplayName("Login and open Not my posts")
    @BeforeEach
    void openLoginPage() {
        getDriver().get(urlLoginPage);
        logIn(username, password);
        getDriver().findElement(btnNotMyPosts).click();
    }

    @DisplayName("Check open Not my posts")
    @Order(1)
    @Test
    void openFeedNotMyPosts() {
        assertThat(getDriver().getCurrentUrl(), is(equalTo(urlNotMyPostsPage)));
    }

    @DisplayName("Check count posts cards")
    @Order(2)
    @Test
    void countPostCards() {
        List<WebElement> cards = getDriver().findElements(cardPost);
        Integer countCards = cards.size();
        assertEquals(4, countCards);
    }


    @DisplayName("Check next page")
    @Order(3)
    @Test
    void goToTheNextPage() {
        getDriver().findElement(btnNextNotMyPostsPage).click();
        new Actions(getDriver()).pause(1000).perform();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlNextNotMyPostsPage)));
    }


    @DisplayName("Check step on pages")
    @Order(4)
    @Test
    void goToThePrevPage() {
        getDriver().findElement(btnNextNotMyPostsPage).click();
        getDriver().findElement(btnPrevNotMyPostsPage).click();
        new Actions(getDriver()).pause(1000).perform();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlPrevNotMyPostsPage)));
    }

    @DisplayName("Check sorting posts by date DESC")
    @Order(5)
    @Test
    void sortingPostsByDateDESC() {
        getDriver().findElement(btnOrderDESC).click();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlNotMyPostsPageDESC)));
    }

    @DisplayName("Check sorting posts by date ASC")
    @Order(6)
    @Test
    void sortingPostsByDateASC() {
        getDriver().findElement(btnOrderDESC).click();
        getDriver().findElement(btnOrderASC).click();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlNotMyPostsPageASC)));
    }

    @DisplayName("Viewing post")
    @Order(7)
    @Test
    void viewingPost() {
        getDriver().findElement(firstCardPost).click();
        assertTrue(getDriver().findElement(titlePost).isDisplayed());
    }
}