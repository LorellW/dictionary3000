package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract class WordCardsTest extends AbstractTest {

    protected WebElement nextButton;
    protected WebElement enField;
    protected WebElement ruField;

    protected WordCardsTest(){
        successfulLogin();
        driver.get("http://localhost:8080/wordcards");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Word cards"));

        initialiseElements();
    }

    private void initialiseElements(){
        this.nextButton = driver.findElement(By.id("start-next-button"));
        this.enField = driver.findElement(By.id("en-word-field"));
        this.ruField = driver.findElement(By.id("ru-word-field"));
    }

    protected void clear(String username, String enWord){
        sendUpdateQuery(String.format("""
                update user_words uw\s
                set en_translated = false,
                ru_translated = false\s
                where uw.id_user = (
                select id from users u\s
                where u.username = '%s')
                and uw.id_word = (
                select id from words w\s
                where w.word_en = '%s');
                """,username,enWord));
    }
}
