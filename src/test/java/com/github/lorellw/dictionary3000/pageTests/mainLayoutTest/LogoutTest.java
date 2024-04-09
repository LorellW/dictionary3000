package com.github.lorellw.dictionary3000.pageTests.mainLayoutTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.time.Duration;

@Test
public class LogoutTest extends AbstractTest {

    public void logoutTest(){
        successfulLogin();
        driver.findElement(By.id("logout-button")).click();
        new WebDriverWait(driver,Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));
    }
}
