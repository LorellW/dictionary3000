package com.github.lorellw.dictionary3000;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

@SpringBootTest
public class DictionaryTest extends SetupViewTest{
    private static WebDriver driver = SetupViewTest.getDriver();


    @Test
    void dictionaryViewTest(){
        driver.get("http://localhost:8080/3000");
        new WebDriverWait(driver, Duration.ofSeconds(30),Duration.ofSeconds(1)).until(titleIs("Dictionary 3000"));

        var filterField = driver.findElement(By.id("filter-text-field-0"));
        var statusBox = driver.findElement(By.id("status-combo-box-0"));

        filterField.sendKeys("go");
        statusBox.sendKeys("New");
    }
}
