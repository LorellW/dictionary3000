package com.github.lorellw.dictionary3000.pageTests.translateViewTests;

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
public class TranslateAnswersTest extends TranslateViewTest {

    public void differentVariants() {
        nextButton.click();
        var temp = new HashSet<String>();
        answerButtons.forEach(webElement -> assertTrue(temp.add(webElement.getText())));
    }

    public void rightAnswer() throws SQLException, InterruptedException {
        var wordEn = textField.getAttribute("value");
        var resultSet = sendSelectQuery(String.format("""
                SELECT * FROM words w\s
                WHERE w.word_en = '%s'
                """, wordEn));
        assertTrue(resultSet.next());
        long id = resultSet.getLong(1);
        var wordRu = resultSet.getString(3);
        answerButtons.stream()
                .filter(webElement -> webElement.getText().equals(wordRu))
                .findAny()
                .ifPresent(WebElement::click);
        Thread.sleep(500);
        var enTranslated = sendSelectQuery(String.format("""
                SELECT uw.en_translated FROM user_words uw\s
                WHERE uw.id_word = %d 
                AND
                uw.id_user = %d
                """, id, getCurrentUserId()));
        assertTrue(enTranslated.next());
        assertTrue(enTranslated.getBoolean(1));
        answerButtons.forEach(webElement -> assertEquals(webElement.getAttribute("aria-disabled"), "true"));

        //todo Clear
        clear();
    }
}
