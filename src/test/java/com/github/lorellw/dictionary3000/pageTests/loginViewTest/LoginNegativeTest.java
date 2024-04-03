package com.github.lorellw.dictionary3000.pageTests.loginViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import com.github.lorellw.dictionary3000.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Test
public class LoginNegativeTest extends AbstractTest {

    public void incorrectUserName() {
        driver.get("http://localhost:8080/login");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"), "AbsolutelyWrongUserName");
        sendInput(By.id("vaadinLoginPassword"), Util.getPropertyByKey("pass"));

        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe("http://localhost:8080/login?error"));
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.titleIs("Login"));

        var slotAttribute = driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("div")).getAttribute("slot");

        assertEquals(slotAttribute,"error-message");
    }

    public void incorrectPassword(){
        driver.get("http://localhost:8080/login");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"), Util.getPropertyByKey("login"));
        sendInput(By.id("vaadinLoginPassword"), "AbsolutelyWrongPassword");

        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe("http://localhost:8080/login?error"));
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.titleIs("Login"));

        var slotAttribute = driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("div")).getAttribute("slot");

        assertEquals(slotAttribute,"error-message");
    }

    public void emptyInputs() {
        driver.get("http://localhost:8080/login");

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"), "");
        sendInput(By.id("vaadinLoginPassword"), "");
        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        assertEquals(driver.getCurrentUrl(), "http://localhost:8080/login");

    }
}
