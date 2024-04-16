package com.github.lorellw.dictionary3000.pageTests;

import com.github.lorellw.dictionary3000.config.ConnectConfig;
import com.github.lorellw.dictionary3000.config.WebDriverConfig;
import com.github.lorellw.dictionary3000.util.Util;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Duration;

public abstract class AbstractTest {
    protected WebDriver driver;
    private final Connection connection;

    public AbstractTest() {
        this.driver = WebDriverConfig.getConfiguredDriver();
        this.connection = ConnectConfig.getConnection();
    }

    protected void sendInput(By locator, String text) {
        driver.findElement(locator)
                .findElement(By.tagName("input"))
                .sendKeys(text);
    }

    protected void sendInput(WebElement element, By locator, String text) {
        element.findElement(locator)
                .findElement(By.tagName("input"))
                .sendKeys(text);
    }

    protected void successfulLogin(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"), Util.getPropertyByKey("login"));
        sendInput(By.id("vaadinLoginPassword"),Util.getPropertyByKey("pass"));

        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("About"));
    }

    @SneakyThrows
    protected ResultSet sendSelectQuery(String query) {
        return connection.prepareStatement(query)
                .executeQuery();
    }
    @SneakyThrows
    protected void sendUpdateQuery(String query) {
        var statement = connection.createStatement();
        statement.executeUpdate(query);
    }
    @AfterClass
    public void quit() {
        driver.quit();
    }
}
