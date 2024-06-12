package com.github.lorellw.dictionary3000.pageTests.wordFromLettersTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class LettersTest extends WordFromLettersTest{

    public void lettersTest() throws SQLException {
        nextButton.click();
        var lettersLayout = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("letters")));
        var temp = lettersLayout
                .findElements(By.tagName("vaadin-button"))
                .stream().map(WebElement::getText).toArray(String[]::new);
        assertTrue(temp.length>0);
        var actualEnWord = Arrays.stream(findWord(taskOutput.getAttribute("value"))
                .getEn()
                .split(""))
                .toArray(String[]::new);
        assertEquals(actualEnWord.length,temp.length);
        Arrays.sort(temp);
        Arrays.sort(actualEnWord);
        assertEquals(temp,actualEnWord);
    }

}
