package com.github.lorellw.dictionary3000;

import com.github.lorellw.dictionary3000.config.WebDriverConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public abstract class AbstractTest {
    protected WebDriver driver;
    public AbstractTest(){
        this.driver = WebDriverConfig.getConfiguredDriver();
    }

    @AfterClass
    public void quit(){
        driver.quit();
    }
}
