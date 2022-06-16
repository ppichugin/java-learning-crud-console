package kz.pichugin.repository.jdbc;

import kz.pichugin.sql.ConnectionMySql;
import kz.pichugin.sql.SqlHelper;

import java.sql.Connection;

public abstract class AbstractJdbcRepository {
    protected final Connection connection = ConnectionMySql.get().getSqlConnection();
    protected SqlHelper sqlHelper;
}