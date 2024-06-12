package com.github.lorellw.dictionary3000.pageTests.translateViewTest;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class RightAnswerTest extends TranslateViewTest{

    public void rightAnswer() throws SQLException, InterruptedException {
        nextButtonClicked();
        var temp = findWord(textField.getAttribute("value"));
        answerButtons.stream()
                .filter(webElement -> webElement.getText().equals(temp.getRu()))
                .findAny()
                .ifPresent(WebElement::click);
        Thread.sleep(500);
        var translated = checkStatus(temp.getId());
        assertTrue(translated.next());
        assertTrue(translated.getBoolean(1));
        answerButtons.forEach(webElement -> assertEquals(webElement.getAttribute("aria-disabled"), "true"));
    }
}
