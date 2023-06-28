package com.github.lorellw.dictionary3000;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SetupViewTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\webdrivers\\chrome\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Lorell_W\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("http://localhost:8080/login");
        new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1)).until(titleIs("Login"));

        var login = "user";
        var password = "userpass";

        var loginTextField = driver.findElement(By.id("vaadinLoginUsername"));
        var passwordField = driver.findElement(By.id("vaadinLoginPassword"));
        var loginButton = driver.findElement(By.xpath("//*[@id=\"login-view-0\"]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

        loginTextField.sendKeys(login);
        passwordField.click();
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    @AfterAll
    public static void closeDriver(){
        driver.close();
    }
}
