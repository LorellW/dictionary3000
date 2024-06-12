package com.github.lorellw.dictionary3000.pageTests.wordFromLettersTest;

import com.github.lorellw.dictionary3000.util.PojoWord;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

@Test
public class WrongAnswerTest extends WordFromLettersTest{

    public void wrongAnswerTest() throws SQLException {
        nextButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .not(drvr -> taskOutput.getAttribute("value").equals("")));
        checkButton.click();
        var pojoWord = findWord(taskOutput.getAttribute("value"));
        assertTrue(answerInput.getAttribute("value").contains("("+pojoWord.getEn()+")"));
    }
}
