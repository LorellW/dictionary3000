package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import com.github.lorellw.dictionary3000.util.PojoWord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.sql.SQLException;
import java.time.Duration;

abstract class WordCardsTest extends AbstractTest {

    protected WebElement nextButton;
    protected WebElement enField;
    protected WebElement ruField;

    protected WordCardsTest() {
        successfulLogin();
        driver.get("http://localhost:8080/wordcards");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Word cards"));

        initialiseElements();
    }

    private void initialiseElements() {
        this.nextButton = driver.findElement(By.id("start-next-button"));
        this.enField = driver.findElement(By.id("en-word-field"));
        this.ruField = driver.findElement(By.id("ru-word-field"));
    }

    @AfterClass
    protected void clearDb(){
        clear();
    }

}
