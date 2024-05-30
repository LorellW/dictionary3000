package com.github.lorellw.dictionary3000.pageTests.translateViewTest;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class LanguageSwapTest extends TranslateViewTest{

    public void languageSwapTest() {
        var enTab = driver.findElement(By.id("en-tab-0"));
        var ruTab = driver.findElement(By.id("ru-tab-0"));
        assertEquals(enTab.getAttribute("aria-selected"),"true");
        assertEquals(ruTab.getAttribute("aria-selected"),"false");
        nextButton.click();
        assertTrue(textField.getAttribute("value").matches("[A-z]+"));
        ruTab.click();
        nextButton.click();
        assertTrue(textField.getAttribute("value").matches("[А-я]+"));
    }
}
