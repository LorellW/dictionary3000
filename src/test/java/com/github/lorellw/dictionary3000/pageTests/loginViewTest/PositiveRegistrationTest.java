package com.github.lorellw.dictionary3000.pageTests.loginViewTest;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.testng.Assert.assertTrue;

@Test
public class PositiveRegistrationTest extends RegistrationTest {

    @Parameters({
            "newLogin", "newPassword"
    })
    public void positiveRegistrationTest(String newLogin, String newPassword) throws SQLException, InterruptedException {
        openRegistrationDialog();
        dataInput(newLogin,newPassword, newPassword);

        Thread.sleep(1000);//todo try some WDW

        var resultSet = sendSelectQuery(String.format("SELECT username FROM users u \n" +
                "WHERE u.username = '%s'",newLogin));
        assertTrue(resultSet.next());

        clear(newLogin);
    }


}
