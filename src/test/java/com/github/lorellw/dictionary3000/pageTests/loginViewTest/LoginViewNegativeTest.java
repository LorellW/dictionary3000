package com.github.lorellw.dictionary3000.pageTests.loginViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import com.github.lorellw.dictionary3000.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

@Test
public class LoginViewNegativeTest extends AbstractTest {

    public void incorrectUserName() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"), "AbsolutelyWrongUserName");
        sendInput(By.id("vaadinLoginPassword"), Util.getPropertyByKey("pass"));

        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe("http://localhost:8080/login?error"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("div")).getAttribute("slot");
    }
}
