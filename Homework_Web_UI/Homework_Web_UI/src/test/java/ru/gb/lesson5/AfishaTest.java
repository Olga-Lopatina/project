package ru.gb.lesson5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AfishaTest {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupBrowser() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
        driver.get("https://afisha.ru");

    }

    @Test
    void goToChangeWorldTest(){
        driver.findElements(By.xpath("a[href=/movie/]"));
        //webDriverWait.until()
        driver.findElements(By.xpath("//button[text()='Изменившие мир']"));

        Assertions.assertTrue(driver.findElement(By.xpath("//button[text()='Изменившие мир']")).isDisplayed());

    }

    @AfterEach
    void tearDown() {

        driver.quit();
    }



}
