package com.github.lorellw.dictionary3000.pageTests.translateViewTests;

import com.github.lorellw.dictionary3000.util.PojoWord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;

import static org.testng.Assert.*;

@Test
public class WrongAnswerTest extends TranslateViewTest {

    public void wrongAnswer() throws SQLException, InterruptedException {
        nextButtonClicked();
        var pojoWord = findWord(textField.getAttribute("value"));
        answerButtons.stream()
                .filter(webElement -> !webElement.getText().equals(pojoWord.getRu()))
                .findAny()
                .ifPresent(WebElement::click);
        Thread.sleep(500);
        var translated = checkStatus(pojoWord.getId());
        assertTrue(translated.next());
        assertFalse(translated.getBoolean(1));
        answerButtons.forEach(webElement ->{
            System.err.println(webElement.getText());
            assertEquals(webElement.getAttribute("aria-disabled"), "true");
        });

    }
}
