package com.github.lorellw.dictionary3000.pageTests.loginViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationTest extends AbstractTest {

    protected void openRegistrationDialog(){
        driver.get("http://localhost:8080/login");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.titleIs("Login"));
        driver.findElement(By.id("registration-button")).click();
    }

    protected void dataInput(String newLogin, String newPassword, String confirmedPassword) {
        var dialog = (new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(innerDriver -> driver.findElement(By.tagName("vaadin-dialog-overlay"))
                        .findElement(By.tagName("vaadin-vertical-layout"))));
        sendInput(dialog,By.id("login-input"), newLogin);
        sendInput(dialog,By.id("pass-input"), newPassword);
        sendInput(dialog,By.id("confirm-pass-input"), confirmedPassword);
        dialog.click();

        dialog.findElement(By.id("sign-up-button")).click();
    }

    protected void clear(String temp){
        sendUpdateQuery(String.format("""
                delete from user_role ur \s
                where ur.id_user = (\s
                select id from users u \s
                where u.username = '%s'\s
                )""",temp));
        sendUpdateQuery(String.format("""
                delete from users u \s
                where u.username = '%s'
                """,temp));
    }
}
