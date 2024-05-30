package com.github.lorellw.dictionary3000.pageTests.wordFromLettersTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WordFromLettersTest extends AbstractTest {

    protected WebElement nextButton;
    protected WebElement checkButton;
    protected WebElement resetButton;
    protected WebElement taskOutput;
    protected WebElement answerInput;

    protected WordFromLettersTest(){
        successfulLogin();
        driver.get("http://localhost:8080/wordsfromchars");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Words from characters"));
        initializeElements();
    }

    private void initializeElements(){
        nextButton = driver.findElement(By.id("next-button"));
        checkButton = driver.findElement(By.id("check-button"));
        resetButton = driver.findElement(By.id("reset-button"));
        taskOutput = driver.findElement(By.id("task-output"));
        answerInput = driver.findElement(By.id("answer-input"));
    }
}
