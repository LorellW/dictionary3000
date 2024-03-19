package com.github.lorellw.dictionary3000.pageTests;

import com.github.lorellw.dictionary3000.util.Util;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import static org.testng.Assert.*;

@Test
public class LoginViewTest extends AbstractTest{

    @SneakyThrows
    public void loginPositiveTest(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"),Util.getPropertyByKey("login"));
        sendInput(By.id("vaadinLoginPassword"),Util.getPropertyByKey("pass"));

        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        String currentTitle = driver.getTitle();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions
                        .not(ExpectedConditions.titleIs(currentTitle)));
        assertEquals(driver.getTitle(),"About");
    }

    private void sendInput(By locator, String text){
        driver.findElement(locator)
                .findElement(By.tagName("input"))
                .sendKeys(text);
    }
}
