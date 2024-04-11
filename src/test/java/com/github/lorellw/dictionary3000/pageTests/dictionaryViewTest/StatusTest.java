package com.github.lorellw.dictionary3000.pageTests.dictionaryViewTest;

import com.github.lorellw.dictionary3000.enums.Status;
import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import com.github.lorellw.dictionary3000.util.PojoWord;
import com.vaadin.flow.component.combobox.ComboBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
public class StatusTest extends DictionaryTest {

    public void statusTest() {
        assertEquals(selectWithComboBox(Status.NEW), selectWithoutComboBox(Status.NEW));
        assertEquals(selectWithComboBox(Status.STUDIED), selectWithoutComboBox(Status.STUDIED));
        assertEquals(selectWithComboBox(Status.ONSTUDY), selectWithoutComboBox(Status.ONSTUDY));
    }


    private List<PojoWord> selectWithComboBox(Status status) {
        var select = driver.findElement(By.tagName("vaadin-select"));
        select.sendKeys(status.getName());
        return getContentFromGrid();
    }

    private List<PojoWord> selectWithoutComboBox(Status status) {
        return getContentFromGrid()
                .stream()
                .filter(pojoWord -> pojoWord.getStatus() == status)
                .toList();
    }
}
