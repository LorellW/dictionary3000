package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Test
public class StartAndNextText extends WordCardsTest {

    public void startAndNextButtonTest(){
        var button = driver.findElement(By.id("start-next-button"));
        var enField = driver.findElement(By.id("en-word-field"));
        var ruField = driver.findElement(By.id("ru-word-field"));
        assertEquals(enField.getAttribute("value"),"");
        assertEquals(ruField.getAttribute("value"),"");
        assertEquals(button.getText(),"Start");
        button.click();
        assertNotEquals(enField.getAttribute("value"),"");
        assertEquals(ruField.getAttribute("value"),"");
        assertEquals(button.getText(),"Next");
    }
}
