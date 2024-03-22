package com.github.lorellw.dictionary3000.pageTests;

import com.github.lorellw.dictionary3000.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public abstract class AbstractTest {
    protected WebDriver driver;
    public AbstractTest(){
        this.driver = WebDriverConfig.getConfiguredDriver();
    }

    protected void sendInput(By locator, String text){
        driver.findElement(locator)
                .findElement(By.tagName("input"))
                .sendKeys(text);
    }

    @AfterClass
    public void quit(){
        driver.quit();
    }
}
