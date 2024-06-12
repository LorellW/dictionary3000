package com.github.lorellw.dictionary3000.pageTests.wordFromLettersTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;

@Test
public class RightAnswerTest extends WordFromLettersTest {

    public void rightAnswerTest() throws SQLException, InterruptedException {
        nextButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .not(drvr -> taskOutput.getAttribute("value").equals("")));
        var enWord = findWord(taskOutput.getAttribute("value")).getEn();
        var letters = enWord.split("");
        var lettersLayout = driver.findElement(By.id("letters")).findElements(By.tagName("vaadin-button"));
        for (String temp : letters) {
            lettersLayout.stream()
                    .filter(webElement -> webElement.getText().equals(temp) && (webElement.getAttribute("tabIndex").equals("0")))
                    .findAny()
                    .ifPresent(WebElement::click);
        }
        checkButton.click();
        Thread.sleep(500);
        var resultSet = sendSelectQuery(String.format("""
                SELECT uw.competently FROM user_words uw
                JOIN words w ON uw.id_word = w.id
                WHERE w.word_en = '%s'
                AND uw.id_user = %d
                """, enWord, getCurrentUserId()));
        assertTrue(resultSet.next());
        assertTrue(resultSet.getBoolean(1));
    }

    @AfterClass
    private void clearDb(){
        clear();
    }
}

