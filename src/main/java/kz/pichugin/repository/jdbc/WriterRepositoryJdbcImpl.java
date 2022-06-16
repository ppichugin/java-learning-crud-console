package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Post;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;
import kz.pichugin.sql.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public Writer save(Writer writer) {
        if (writer == null) {
            return null;
        }
        if (writer.isNew()) {
            sqlHelper.execute("INSERT INTO writers (first_name, last_name) VALUE (?,?)", ps -> {
                ps.setString(1, writer.getFirstName());
                ps.setString(2, writer.getLastName());
                ps.executeUpdate();
                return null;
            });
        } else {
            System.out.println("Writer is not new. Cannot be saved.");
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        if (writer == null) {
            return null;
        }
        if (writer.isNew()) {
            System.out.println("Writer is new, can not be updated.");
            return null;
        } else {
            sqlHelper.execute("UPDATE writers SET first_name=?, last_name=? WHERE id=?", ps -> {
                ps.setString(1, writer.getFirstName());
                ps.setString(2, writer.getLastName());
                ps.setLong(3, writer.getId());
                ps.executeUpdate();
                return null;
            });
            return writer;
        }
    }

    @Override
    public Writer getById(Long id) {
        return sqlHelper.execute("SELECT * FROM writers WHERE id=?", ps -> {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return getWriter(id, rs);
        });
    }

    @Override
    public void deleteById(Long id) {
        sqlHelper.execute("DELETE FROM writers WHERE id=?", ps -> {
            ps.setLong(1, id);
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public List<Writer> getAll() {
        return sqlHelper.execute("SELECT * FROM writers", ps -> {
            List<Writer> writers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Writer writer = getWriter(rs.getLong("id"), rs);
                writers.add(writer);
            }
            return writers;
        });
    }

    private Writer getWriter(Long writerId, ResultSet rs) throws SQLException {
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        List<Post> posts = PostRepositoryJdbcImpl.getInstance().getPostsByWriterId(writerId);
        return new Writer(writerId, first_name, last_name, posts);
    }
}