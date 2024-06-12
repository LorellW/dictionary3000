package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import com.github.lorellw.dictionary3000.enums.Status;
import com.github.lorellw.dictionary3000.util.PojoWord;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Test
public class KnowButtonTest extends WordCardsTest {
    public void knowButtonTest() throws SQLException {
        nextButton.click();
        var enWord = enField.getAttribute("value");
        var word = findWord(enWord);

        assertNotEquals(word.getStatus(), Status.STUDIED);

        var knowButton = driver.findElement(By.id("know-button"));
        knowButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(drvr -> !ruField.getAttribute("value").equals(""));
        var ruWord = ruField.getAttribute("value");
        word = findWord(enWord);
        assertEquals(ruWord,word.getRu());
        assertEquals(word.getStatus(), Status.STUDIED);

    }

}
