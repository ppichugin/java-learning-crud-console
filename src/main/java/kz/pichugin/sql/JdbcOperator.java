package kz.pichugin.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcOperator {
    private static Connection connection;

    private JdbcOperator() {
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = ConnectionMySql.getConnection();
        }
        return connection;
    }

    public static <T> T execute(String sqlStatement, ProcessSqlRequest<T> blockCode) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sqlStatement)) {
            return blockCode.run(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T transactionalExecute(SqlTransaction<T> executor) {
        try {
            Connection conn = getConnection();
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
