package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Label;
import kz.pichugin.model.Post;
import kz.pichugin.model.PostStatus;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.PostRepository;
import kz.pichugin.sql.JdbcOperator;
import kz.pichugin.util.ToLocalDateTimeConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryJdbcImpl extends AbstractJdbcRepository implements PostRepository {
    private final LabelRepositoryJdbcImpl labelRepositoryJdbc = new LabelRepositoryJdbcImpl();
    private final WriterRepositoryJdbcImpl writerRepositoryJdbc = new WriterRepositoryJdbcImpl();

    @Override
    public Post save(Post post) {
        int saveResult = 0;
        if (post.isNew()) {
            saveResult = JdbcOperator.transactionalExecute(conn -> {
                int updatedRows;
                try (PreparedStatement ps = conn.
                        prepareStatement("INSERT INTO posts (content, created, post_status, writer_id) VALUES (?,?,?,?)")) {
                    ps.setString(1, post.getContent());
                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(3, String.valueOf(post.getPostStatus()));
                    ps.setLong(4, post.getWriter().getId());
                    updatedRows = ps.executeUpdate();
                }
                return updatedRows;
            });
        } else {
            System.out.println("Post is not new. Cannot be saved.");
        }
        return saveResult > 0 ? post : null;
    }

    @Override
    public Post update(Post post) {
        if (post.isNew()) {
            System.out.println("Post is new, can not be updated.");
            return null;
        } else {
            int resultOfUpdate = JdbcOperator.transactionalExecute(conn -> {
                int updatedRows;
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE posts SET content = ?, updated = ?, post_status = ? WHERE id = ?")) {
                    ps.setString(1, post.getContent());
                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(3, String.valueOf(post.getPostStatus()));
                    ps.setLong(4, post.getId());
                    updatedRows = ps.executeUpdate();
                }
                return updatedRows;
            });
            if (resultOfUpdate > 0) {
                System.out.println("Updated!");
                return post;
            } else {
                System.out.println("Post was not updated");
                return null;
            }
        }
    }

    @Override
    public Post getById(Long id) {
        final Post[] post = new Post[1];
        JdbcOperator.execute("SELECT * FROM posts WHERE id=?", ps -> {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            List<Label> labelsByPostId = labelRepositoryJdbc.getLabelsByPostId(id);
            post[0] = getPost(id, labelsByPostId, rs);
            return null;
        });
        return post[0];
    }

    public List<Post> getPostsByWriterId(Long writerId) {
        return JdbcOperator.execute("SELECT * FROM posts WHERE writer_id=?", ps -> {
            ps.setLong(1, writerId);
            ResultSet rs = ps.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                Long postId = rs.getLong("id");
                List<Label> labelsByPostId = labelRepositoryJdbc.getLabelsByPostId(postId);
                posts.add(getPost(postId, labelsByPostId, rs));
            }
            return posts;
        });
    }

    @Override
    public void deleteById(Long id) {
        JdbcOperator.execute("UPDATE posts SET post_status = ? WHERE posts.id=?", ps -> {
            ps.setString(1, String.valueOf(PostStatus.DELETED));
            ps.setLong(2, id);
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        JdbcOperator.execute("SELECT * FROM posts", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                List<Label> labelsByPostId = labelRepositoryJdbc.getLabelsByPostId(id);
                postList.add(getPost(id, labelsByPostId, rs));
            }
            return null;
        });
        return postList;
    }

    private Post getPost(Long id, List<Label> labelsByPostId, ResultSet rs) throws SQLException {
        String content = rs.getString("content");
        LocalDateTime created = ToLocalDateTimeConverter.convert(rs.getTimestamp("created"));
        LocalDateTime updated = ToLocalDateTimeConverter.convert(rs.getTimestamp("updated"));
        PostStatus postStatus = PostStatus.valueOf(rs.getString("post_status"));

        Writer writer = writerRepositoryJdbc.getById(rs.getLong("writer_id"));

        return new Post(id, content, created, updated, postStatus, labelsByPostId, writer);
    }
}