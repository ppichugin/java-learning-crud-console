package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;
import kz.pichugin.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.util.List;

public class WriterRepositoryJdbcImpl extends AbstractJdbcRepository implements WriterRepository {
    private static WriterRepositoryJdbcImpl instance;

    private WriterRepositoryJdbcImpl() {
        sqlHelper = new SqlHelper(() -> connection);
    }

    public static WriterRepositoryJdbcImpl getInstance() {
        if (instance == null) {
            instance = new WriterRepositoryJdbcImpl();
        }
        return instance;
    }

    @Override
    public Writer save(Writer type) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("")) {

            }

            return null;
        });
        return null;
    }

    @Override
    public Writer update(Writer type) {
        return null;
    }

    @Override
    public Writer getById(Long aLong) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Writer> getAll() {
        return null;
    }
}