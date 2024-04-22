package com.github.lorellw.dictionary3000.pageTests.wordCardsViewTest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Test
public class StartAndNextText extends WordCardsTest {

    public void startAndNextButtonTest(){
        assertEquals(enField.getAttribute("value"),"");
        assertEquals(ruField.getAttribute("value"),"");
        assertEquals(nextButton.getText(),"Start");
        nextButton.click();
        assertNotEquals(enField.getAttribute("value"),"");
        assertEquals(ruField.getAttribute("value"),"");
        assertEquals(nextButton.getText(),"Next");
    }
}
