package com.github.lorellw.dictionary3000.pageTests.loginViewTest;

import com.github.lorellw.dictionary3000.exeption.WebElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

@Test
public class NegativeRegistrationTest extends RegistrationTest {
    @Parameters(
            "existingUsername"
    )
    public void usernameAlreadyExists(String existingUsername) {
        openRegistrationDialog();
        dataInput(existingUsername, "justPass", "justPass");
        var message = findMessageDialog();
        assertTrue(message.getText().contains("Username already exists"));
    }

    @Parameters({
            "newLogin","newPassword"
    })
    public void passIsNotConfirmed(String newLogin, String newPassword) {
        openRegistrationDialog();
        dataInput(newLogin,newPassword, newPassword+"something");
        var message = findMessageDialog();
        assertTrue(message.getText().contains("Password is not confirmed"));
    }

    private WebElement findMessageDialog(){
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(innerDriver -> {
                    WebElement temp = null;
                    try {
                        temp = driver.findElements(By.tagName("vaadin-dialog-overlay"))
                                .stream()
                                .filter(webElement -> "Message".equals(webElement.getAttribute("ariaLabel")))
                                .findFirst()
                                .orElseThrow(new WebElementNotFoundException("vaadin-dialog-overlay"));
                    } catch (WebElementNotFoundException e) {
                        e.printStackTrace();
                    }
                    return temp;
                });
    }
}
