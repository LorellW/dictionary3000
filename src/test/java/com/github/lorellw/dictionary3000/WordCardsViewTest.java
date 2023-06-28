package com.github.lorellw.dictionary3000;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

@SpringBootTest
public class WordCardsViewTest extends SetupViewTest{
    private static WebDriver driver = SetupViewTest.getDriver();

    @Test
    public void wordCardsTest(){
        driver.get("http://localhost:8080/wordcards");
        new WebDriverWait(driver, Duration.ofSeconds(30),Duration.ofSeconds(1)).until(titleIs("Word cards"));

        var enTextField = driver.findElement(By.id("input-vaadin-text-field-6"));
        var ruTextField = driver.findElement(By.id("input-vaadin-text-field-7"));
        var knowButton = driver.findElement(By.id("know-button-0"));
        var dontKnowButton = driver.findElement(By.id("dont-know-button-0"));
        var nextButton = driver.findElement(By.id("next-button-0"));


    }
}
