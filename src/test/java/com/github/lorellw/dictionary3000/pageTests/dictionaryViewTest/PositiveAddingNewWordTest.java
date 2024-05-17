package com.github.lorellw.dictionary3000.pageTests.dictionaryViewTest;

import com.github.lorellw.dictionary3000.enums.Status;
import com.github.lorellw.dictionary3000.util.PojoWord;
import com.github.lorellw.dictionary3000.util.Util;
import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

@Test
public class PositiveAddingNewWordTest extends DictionaryTest {
    @Parameters({
            "newWordEn",
            "newWordRu"
    })
    public void addingTest(String newWordEn, String newWordRu) throws SQLException, InterruptedException {
        driver.findElement(By.id("add-button")).click();
        sendInput(By.id("input-word-en"), newWordEn);
        sendInput(By.id("input-word-ru"), newWordRu);
        driver.findElement(By.id("save-button")).click();

        Thread.sleep(10);

        var resultSet = sendSelectQuery(String.format("""
                SELECT * FROM user_words uw \s
                JOIN words w ON uw.id_word = w.id \s
                JOIN users u ON uw.id_user = u.id \s
                WHERE u.username = '%s' \s
                AND w.word_en = '%s'
                """, Util.getPropertyByKey("login"), newWordEn));
        var list = resultSetToList(resultSet);

        assertEquals(list.size(), 1);
        assertEquals(new PojoWord(0L, newWordEn, newWordRu, Status.NEW), list.get(0));

        clear(newWordEn);
    }
}
