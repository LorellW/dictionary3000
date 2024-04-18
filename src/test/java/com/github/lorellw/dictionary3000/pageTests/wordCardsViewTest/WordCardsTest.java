package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WordCardsTest extends AbstractTest {

    protected WordCardsTest(){
        successfulLogin();
        driver.get("http://localhost:8080/wordcards");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Word cards"));
    }
}
