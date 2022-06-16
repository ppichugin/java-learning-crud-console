package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Label;
import kz.pichugin.repository.LabelRepository;
import kz.pichugin.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    public Label save(Label label) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO labels (name, post_id) VALUES (?,?)")) {
                ps.setString(1, label.getName());
                ps.setLong(2, label.getPostId());
                ps.executeUpdate();
            }
            return null;
        });
        return label;
    }

    @Override
    public Label update(Label label) {
        sqlHelper.execute("UPDATE labels SET name = ? WHERE id = ?", ps -> {
            ps.setString(1, label.getName());
            ps.setLong(2, label.getId());
            ps.executeUpdate();
            return null;
        });
        return label;
    }

    @Override
    public Label getById(Long aLong) {
        final Label[] label = {null};
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM labels WHERE id = ?")) {
                ps.setLong(1, aLong);
                ResultSet rs = ps.executeQuery();
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Long postId = rs.getLong("post_id");
                label[0] = new Label(id, name, postId);
            }
            return null;
        });
        return label[0];
    }

    @Override
    public void deleteById(Long aLong) {
        sqlHelper.execute("DELETE FROM labels WHERE id = ?", ps -> {
            ps.setLong(1, aLong);
            ps.executeQuery();
            return null;
        });
    }

    @Override
    public List<Label> getAll() {
        return sqlHelper.execute("SELECT * FROM labels", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Label> labels = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Long postId = rs.getLong("post_id");
                labels.add(new Label(id, name, postId));
            }
            return labels;
        });
    }

    public List<Label> getLabelsByPostId(Long postId) {
        return sqlHelper.execute("SELECT * FROM labels WHERE post_id=?", ps -> {
            ps.setLong(1, postId);
            ResultSet rs = ps.executeQuery();
            List<Label> labels = new ArrayList<>();
            while (rs.next()) {
                Long labelId = rs.getLong("id");
                String name = rs.getString("name");
                labels.add(new Label(labelId, name, postId));
            }
            return labels;
        });
    }
}