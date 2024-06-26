package com.github.lorellw.dictionary3000.pageTests;

import com.github.lorellw.dictionary3000.config.ConnectConfig;
import com.github.lorellw.dictionary3000.config.WebDriverConfig;
import com.github.lorellw.dictionary3000.util.PojoWord;
import com.github.lorellw.dictionary3000.util.Util;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTest {
    protected WebDriver driver;
    private final Connection connection;

    public AbstractTest() {
        this.driver = WebDriverConfig.getConfiguredDriver();
        this.connection = ConnectConfig.getConnection();
    }

    protected void sendInput(By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(100))
                .until(ExpectedConditions.presenceOfElementLocated(locator))
                .findElement(By.tagName("input"))
                .sendKeys(text);
    }

    protected void sendInput(WebElement element, By locator, String text) {
        element.findElement(locator)
                .findElement(By.tagName("input"))
                .sendKeys(text);
    }

    protected void successfulLogin(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("Login"));

        sendInput(By.id("vaadinLoginUsername"), Util.getPropertyByKey("login"));
        sendInput(By.id("vaadinLoginPassword"),Util.getPropertyByKey("pass"));

        driver.findElement(By.tagName("vaadin-login-form-wrapper"))
                .findElement(By.tagName("vaadin-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.titleIs("About"));
    }

    protected List<PojoWord> resultSetToList(ResultSet set) throws SQLException {
        var list = new ArrayList<PojoWord>();
        while (set.next()) {
            long id = set.getLong("id_word");
            String en = set.getString("word_en");
            String ru = set.getString("word_ru");
            String status;
            var competently = set.getBoolean("competently");
            var enTranslated = set.getBoolean("en_translated");
            var ruTranslated = set.getBoolean("ru_translated");
            if (competently && enTranslated && ruTranslated) {
                status = "vaadin:check";
            } else if (competently || enTranslated || ruTranslated) {
                status = "vaadin:clock";
            } else {
                status = "vaadin:book";
            }
            list.add(new PojoWord(id, en, ru, PojoWord.stringToStatus(status)));
        }
        return list;
    }

    @SneakyThrows
    protected ResultSet sendSelectQuery(String query) {
        return connection.prepareStatement(query)
                .executeQuery();
    }
    @SneakyThrows
    protected void sendUpdateQuery(String query) {
        var statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    @SneakyThrows
    protected long getCurrentUserId(){
        var resultSet = sendSelectQuery(String.format("""
                SELECT id FROM users u\s
                WHERE u.username = '%s'""",
                Util.getPropertyByKey("login")));
        if (resultSet.next()){
            return resultSet.getLong(1);
        }
        return -1;
    }

    protected void clear(){
        updateWord(Long.parseLong(Util.getPropertyByKey("word.complete.id")),true,true,true);
        updateWord(Long.parseLong(Util.getPropertyByKey("word.translatedEu.id")),false,true,false);
        updateWord(Long.parseLong(Util.getPropertyByKey("word.translatedRu.id")),false,false,true);
        updateWord(Long.parseLong(Util.getPropertyByKey("word.new.id")),false,false,false);
        updateWord(Long.parseLong(Util.getPropertyByKey("word.another.id")),false,false,false);
    }

    private void updateWord(long id,boolean competently, boolean euTransl,boolean ruTransl){
        sendUpdateQuery(String.format("""
                UPDATE user_words\s
                  SET competently = %b,\s 
                      en_translated = %b,\s
                   	  ru_translated = %b\s
                  WHERE id_user = %d\s
                  AND id_word = %d
                """,competently,euTransl,ruTransl,getCurrentUserId(),id));
    }

    protected PojoWord findWord( String word) throws SQLException {
        return resultSetToList(sendSelectQuery(String.format("""
                SELECT * FROM user_words uw
                JOIN words w ON uw.id_word = w.id
                WHERE (w.word_en = '%s' OR w.word_ru = '%s')
                AND uw.id_user = '%d'
                """, word, word, getCurrentUserId()))).get(0);
    }

    @AfterClass
    public void quit() {
        driver.quit();
    }
}
