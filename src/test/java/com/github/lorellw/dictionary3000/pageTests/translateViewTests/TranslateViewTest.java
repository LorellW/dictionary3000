package com.github.lorellw.dictionary3000.pageTests.translateViewTests;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.List;

public class TranslateViewTest extends AbstractTest {

    protected WebElement nextButton;
    protected List<WebElement> answerButtons;
    protected WebElement textField;

    protected TranslateViewTest(){
        successfulLogin();
        driver.get("http://localhost:8080/translate");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Translate"));
        initializeElements();
    }

    private void initializeElements(){
        this.nextButton = driver.findElement(By.id("next-button-0"));
        this.answerButtons = driver.findElement(By.id("button-layout"))
                .findElements(By.tagName("vaadin-button"));
        this.textField = driver.findElement(By.id("word-to-translate-text-field-0"));
    }

    protected void nextButtonClicked(){
        nextButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                        .not(ExpectedConditions
                                .attributeContains(textField,"aria-disabled","true")));
    }

    protected ResultSet checkStatus(long idWord){
        return sendSelectQuery(String.format("""
                SELECT uw.en_translated FROM user_words uw\s
                WHERE uw.id_word = %d 
                AND
                uw.id_user = %d
                """, idWord, getCurrentUserId()));
    }
    @AfterClass
    protected void clearDb(){
        clear();
    }
}
