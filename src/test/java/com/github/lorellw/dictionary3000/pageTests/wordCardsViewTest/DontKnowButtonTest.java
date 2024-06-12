package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import com.github.lorellw.dictionary3000.enums.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Test
public class DontKnowButtonTest extends WordCardsTest{
    public void dontKnowButtonTest() throws SQLException, InterruptedException {
        nextButton.click();
        driver.findElement(By.id("know-button")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(drvr -> !ruField.getAttribute("value").isEmpty());
        driver.findElement(By.id("dont-know-button")).click();
        Thread.sleep(500);
        var word = findWord( enField.getAttribute("value"));
        assertEquals(word.getStatus(), Status.NEW);

    }
}
