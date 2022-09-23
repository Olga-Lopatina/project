package ru.gb.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Afisha {
    public static void main (String[] args) {

        WebDriverManager.chromedriver().setup();

        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://afisha.ru");

        WebElement movie = webDriver.findElement(By.xpath("a[href=/movie/]"));
        WebElement changeWorld = webDriver.findElement(By.xpath("//button[text()='Изменившие мир']"));

       // WebDriver.quit();
    }

}
