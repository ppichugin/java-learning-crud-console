package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Label;
import kz.pichugin.repository.LabelRepository;
import kz.pichugin.sql.SqlHelper;

import java.util.List;

public class LabelRepositoryJdbcImpl extends AbstractJdbcRepository implements LabelRepository {
    private static LabelRepositoryJdbcImpl instance;

    private LabelRepositoryJdbcImpl() {
        sqlHelper = new SqlHelper(() -> connection);
    }

    public static LabelRepositoryJdbcImpl getInstance() {
        if (instance == null) {
            instance = new LabelRepositoryJdbcImpl();
        }
        return instance;
    }

    @Override
    public Label save(Label type) {
        return null;
    }

    @Override
    public Label update(Label type) {
        return null;
    }

    @Override
    public Label getById(Long aLong) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Label> getAll() {
        return null;
    }
}