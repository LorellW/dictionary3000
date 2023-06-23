package com.github.lorellw.dictionary3000;

import com.github.lorellw.dictionary3000.services.SecurityService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aspectj.lang.annotation.Before;


import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Dictionary3000ApplicationTests {

    public static WebDriver driver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\webdrivers\\chrome\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Lorell_W\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void closeDriver(){
        driver.close();
    }

    @Test
    void loginViewTest() {
            driver.get("http://localhost:8080/login");
            new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1)).until(titleIs("Login"));

            var login = "user";
            var password = "userpass";
            var incorrectLogin = "wrong";

            var loginTextField = driver.findElement(By.id("vaadinLoginUsername"));
            var passwordField = driver.findElement(By.id("vaadinLoginPassword"));
            var loginButton = driver.findElement(By.xpath("//*[@id=\"login-view-0\"]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

            loginTextField.click();
            loginTextField.sendKeys(incorrectLogin);
            passwordField.click();
            passwordField.sendKeys(password);
            loginButton.click();

            assertEquals(driver.getCurrentUrl(), "http://localhost:8080/login?error");
            new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1)).until(titleIs("Login"));

            loginTextField = driver.findElement(By.id("vaadinLoginUsername"));
            passwordField = driver.findElement(By.id("vaadinLoginPassword"));
            loginButton = driver.findElement(By.xpath("//*[@id=\"login-view-0\"]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"));

            loginTextField.sendKeys(login);
            passwordField.click();
            passwordField.sendKeys(password);
            loginButton.click();

            new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1)).until(titleIs("About"));
            var authenticatedUsername = driver.findElement(By.cssSelector("#ROOT-2521314 > vaadin-app-layout > vaadin-horizontal-layout > h5"));

            assertEquals(authenticatedUsername.getText(), login);
            assertEquals(driver.getCurrentUrl(), "http://localhost:8080/");
    }
}
