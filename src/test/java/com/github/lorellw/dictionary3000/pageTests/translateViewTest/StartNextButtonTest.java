package com.github.lorellw.dictionary3000.pageTests.translateViewTest;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class StartNextButtonTest extends TranslateViewTest {

    public void startNextButtonTest(){
        nextButton.click();
        answerButtons.forEach(button -> assertTrue(button.isEnabled()));
        assertNotEquals(textField.getAttribute("value"), "");
    }
}
