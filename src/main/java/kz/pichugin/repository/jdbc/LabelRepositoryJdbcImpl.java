package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Label;
import kz.pichugin.repository.LabelRepository;
import kz.pichugin.sql.JdbcOperator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryJdbcImpl extends AbstractJdbcRepository implements LabelRepository {

    @Override
    public Label save(Label label) {
        JdbcOperator.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO labels (name, post_id) VALUES (?,?)")) {
                ps.setString(1, label.getName());
                ps.setLong(2, label.getPostId());
                ps.executeUpdate();
            }
            JdbcOperator.transactionalExecute(conn2 -> {
                try (PreparedStatement ps = conn2.prepareStatement("UPDATE posts SET updated = ? WHERE posts.id = ?")) {
                    ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setLong(2, label.getPostId());
                    ps.executeUpdate();
                }
                return null;
            });
            return null;
        });
        return label;
    }

    @Override
    public Label update(Label label) {
        int updatedLabels = JdbcOperator.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE labels SET name = ? WHERE id = ?")) {
                ps.setString(1, label.getName());
                ps.setLong(2, label.getId());
                return ps.executeUpdate();
            }
        });
        return updatedLabels > 0 ? label : null;
    }

    @Override
    public Label getById(Long aLong) {
        final Label[] label = {null};
        JdbcOperator.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM labels WHERE id = ?")) {
                ps.setLong(1, aLong);
                ResultSet rs = ps.executeQuery();
                rs.next();
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
        JdbcOperator.execute("DELETE FROM labels WHERE id = ?", ps -> {
            ps.setLong(1, aLong);
            if (ps.executeUpdate() > 0) {
                System.out.println("Success delete!");
            } else {
                System.out.println("Label with id  delete!");
            }
            return null;
        });
    }

    @Override
    public List<Label> getAll() {
        return JdbcOperator.execute("SELECT * FROM labels", ps -> {
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
        return JdbcOperator.execute("SELECT * FROM labels WHERE post_id=?", ps -> {
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