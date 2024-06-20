package com.github.lorellw.dictionary3000.config;

import com.github.lorellw.dictionary3000.util.Util;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectConfig {
    @Getter
    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(Util.getPropertyByKey("db.url"),
                            Util.getPropertyByKey("db.username"),
                            Util.getPropertyByKey("db.password"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            connection = null;
        }
    }

}
