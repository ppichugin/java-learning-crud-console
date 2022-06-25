package kz.pichugin.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMySql {
    private static final String PROPS = "src/main/resources/jdbc.properties";
    private final Connection sqlConnection;

    private ConnectionMySql() {
        try (FileInputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            final String dbDriver = props.getProperty("driver");
            final String dbUrl = props.getProperty("url");
            final String dbUsername = props.getProperty("username");
            final String dbPassword = props.getProperty("password");

            Class.forName(dbDriver);
            sqlConnection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return ConnectToMySql.INSTANCE.sqlConnection;
    }

    private static class ConnectToMySql {
        private static final ConnectionMySql INSTANCE = new ConnectionMySql();

    }
}