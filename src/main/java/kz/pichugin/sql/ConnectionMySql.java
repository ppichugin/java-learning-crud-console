package kz.pichugin.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMySql {
    private static final String PROPS = "src/main/resources/jdbc.properties";
    private static final ConnectionMySql INSTANCE = new ConnectionMySql();
    private final Connection sqlConnection;

    private ConnectionMySql() {
        try (InputStream is = ConnectionMySql.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            String dbDriver = props.getProperty("driver");
            String dbUrl = props.getProperty("url");
            String dbUsername = props.getProperty("username");
            String dbPassword = props.getProperty("password");

            Class.forName(dbDriver);
            sqlConnection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionMySql get() {
        return INSTANCE;
    }

    public Connection getSqlConnection() {
        return sqlConnection;
    }
}