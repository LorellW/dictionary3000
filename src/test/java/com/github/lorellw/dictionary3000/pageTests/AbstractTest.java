package com.github.lorellw.dictionary3000.pageTests;

import com.github.lorellw.dictionary3000.config.ConnectConfig;
import com.github.lorellw.dictionary3000.config.WebDriverConfig;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractTest {
    protected WebDriver driver;
    private Connection connection;

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
    public void quit() throws SQLException {
        connection.close();
        driver.quit();
    }
}
