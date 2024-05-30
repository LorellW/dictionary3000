package com.github.lorellw.dictionary3000.pageTests.wordFromLettersTest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.*;

@Test
public class ControlButtonsTest extends WordFromLettersTest{

    public void controlButtonTest(){
        beforeRun();
        startButtonClicked();
        checkButtonClicked();
        resetButtonClicked();
    }

    private void beforeRun(){
        assertEquals(taskOutput.getAttribute("value"),"");
        assertNull(nextButton.getAttribute("aria-disabled"));
        assertEquals(checkButton.getAttribute("aria-disabled"),"true");
        assertEquals(resetButton.getAttribute("aria-disabled"),"true");
    }

    private void startButtonClicked(){
        nextButton.click();
        assertEquals(nextButton.getAttribute("aria-disabled"),"true");
        assertNull(checkButton.getAttribute("aria-disabled"));
        assertNull(resetButton.getAttribute("aria-disabled"));
        assertTrue(taskOutput.getAttribute("value").matches("[А-я]+"));
        assertNotNull(new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("letters"))));
    }

    private void checkButtonClicked(){
        checkButton.click();
        assertNull(nextButton.getAttribute("aria-disabled"));
        assertNull(checkButton.getAttribute("aria-disabled"));
        assertNull(resetButton.getAttribute("aria-disabled"));
        assertTrue(answerInput.getAttribute("value").matches("\\([A-z]+\\)"));
    }

    private void resetButtonClicked(){
        resetButton.click();
        assertEquals(answerInput.getAttribute("value"),"");
    }
}
