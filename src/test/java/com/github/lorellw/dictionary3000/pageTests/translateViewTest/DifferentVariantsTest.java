package com.github.lorellw.dictionary3000.pageTests.translateViewTest;

import org.testng.annotations.Test;

import java.util.HashSet;

import static org.testng.Assert.assertTrue;

@Test
public class DifferentVariantsTest extends TranslateViewTest {

    public void differentVariants() {
        nextButton.click();
        var temp = new HashSet<String>();
        answerButtons.forEach(webElement -> assertTrue(temp.add(webElement.getText())));
    }
}
