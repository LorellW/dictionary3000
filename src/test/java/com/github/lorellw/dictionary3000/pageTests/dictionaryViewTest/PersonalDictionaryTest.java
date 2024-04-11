package com.github.lorellw.dictionary3000.pageTests.dictionaryViewTest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class PersonalDictionaryTest extends DictionaryTest {

    public void personalDictionaryTest() {
        var fromGrid = getContentFromGrid();
        var fromDB = getContentFromDB();
        assertEquals(fromGrid.size(), fromDB.size());
        for (int i = 0; i < fromGrid.size(); i++) {
            assertEquals(fromGrid.get(i), fromDB.get(i));
        }
    }
}
