package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import com.github.lorellw.dictionary3000.util.PojoWord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
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

    protected PojoWord findWord(String username, String enWord) throws SQLException {
        return resultSetToList(sendSelectQuery(String.format("""
                SELECT * FROM user_words uw\s
                JOIN users u ON uw.id_user = u.id\s
                JOIN words w ON uw.id_word = w.id\s
                WHERE w.word_en = '%s'
                AND u.username = '%s'
                """, enWord, username))).get(0);
    }

}
