package kz.pichugin.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ProcessSqlRequest<T> {
    T run(PreparedStatement ps) throws SQLException;
}
