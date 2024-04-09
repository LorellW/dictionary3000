package com.github.lorellw.dictionary3000.pageTests.mainLayoutTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class DrawerTest extends AbstractTest {

    public void drawerTest(){
        successfulLogin();
        var drawer = driver.findElement(By.tagName("vaadin-drawer-toggle"));
        boolean condition = Boolean.parseBoolean(drawer.getAttribute("aria-expanded"));
        drawer.click();
        assertNotEquals(Boolean.parseBoolean(drawer.getAttribute("aria-expanded")), condition);
    }
}
