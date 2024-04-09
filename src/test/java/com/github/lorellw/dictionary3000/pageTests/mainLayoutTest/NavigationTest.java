package com.github.lorellw.dictionary3000.pageTests.mainLayoutTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.testng.Assert.assertEquals;

import java.time.Duration;

@Test
public class NavigationTest extends AbstractTest {

    public void navigationTest(){
        successfulLogin();
        var drawer = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/vaadin-app-layout/vaadin-vertical-layout[1]"));
        assertEquals(switchView(drawer,"3000"),"Dictionary 3000|http://localhost:8080/3000");
        assertEquals(switchView(drawer,"wordcards"),"Word cards|http://localhost:8080/wordcards");
        assertEquals(switchView(drawer,"translate"),"Translate|http://localhost:8080/translate");
        assertEquals(switchView(drawer,"wordsfromchars"),"Words from characters|http://localhost:8080/wordsfromchars");
        assertEquals(switchView(drawer,"lessons"),"Grammatical lessons|http://localhost:8080/lessons");
        assertEquals(switchView(drawer,""),"About|http://localhost:8080/");

    }

    private String switchView(WebElement drawer, String href){
        String tempTitle = driver.getTitle();
        var navElement = drawer.findElement(By.xpath(String.format("//a[@href=\"%s\"]",href)));
        navElement.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .not(titleIs(tempTitle)));
        return String.format("%s|%s",driver.getTitle(),driver.getCurrentUrl());
    }
}
