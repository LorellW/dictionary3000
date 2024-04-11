package com.github.lorellw.dictionary3000.pageTests.dictionaryViewTest;

import com.github.lorellw.dictionary3000.pageTests.AbstractTest;
import com.github.lorellw.dictionary3000.util.PojoWord;
import com.github.lorellw.dictionary3000.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DictionaryTest extends AbstractTest {

    protected DictionaryTest(){
        successfulLogin();
        driver.get("http://localhost:8080/3000");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Dictionary 3000"));
    }

    protected List<PojoWord> getContentFromGrid() {
        var words = new ArrayList<PojoWord>();
        var content = driver.findElement(By.className("dictionary-view"))
                .findElement(By.className("content"))
                .findElement(By.className("words-grid"));
        var elements = content.findElements(By.tagName("vaadin-grid-cell-content"))
                .stream()
                .filter(webElement -> !webElement.getAttribute("clientHeight").equals("0"))
                .toList();
        for (int i = 3; i < elements.size(); i += 3) {
            String en = elements.get(i).getAttribute("innerText");
            String ru = elements.get(i + 1).getAttribute("innerText");
            String status = elements.get(i + 2).findElement(By.tagName("vaadin-icon")).getAttribute("icon");
            words.add(new PojoWord(en, ru, PojoWord.stringToStatus(status)));
        }
        return words;
    }

    protected List<PojoWord> getContentFromDB() {
        var words = new ArrayList<PojoWord>();
        var resultSet = sendSelectQuery(String.format("""
                SELECT id FROM users u \s
                WHERE u.username = '%s'""",
                Util.getPropertyByKey("login")));
        int idUser;
        try {
            resultSet.next();
            idUser = resultSet.getInt(1);

            resultSet = sendSelectQuery(String.format("""
                    SELECT * FROM user_words uw \s
                    JOIN words w ON uw.id_word = w.id \s
                    WHERE uw.id_user = %d
                    """,
                    idUser));
            while (resultSet.next()) {
                String en = resultSet.getString("word_en");
                String ru = resultSet.getString("word_ru");
                String status;
                var competently = resultSet.getBoolean("competently");
                var enTranslated = resultSet.getBoolean("en_translated");
                var ruTranslated = resultSet.getBoolean("ru_translated");
                if (competently && enTranslated && ruTranslated) {
                    status = "vaadin:check";
                } else if (competently || enTranslated || ruTranslated) {
                    status = "vaadin:clock";
                } else {
                    status = "vaadin:book";
                }
                words.add(new PojoWord(en, ru, PojoWord.stringToStatus(status)));
            }
        } catch (SQLException ignored) { }
        return words;
    }
}
