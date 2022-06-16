package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Label;
import kz.pichugin.model.Post;
import kz.pichugin.model.PostStatus;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.PostRepository;
import kz.pichugin.sql.SqlHelper;
import kz.pichugin.util.ToLocalDateTimeConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryJdbcImpl extends AbstractJdbcRepository implements PostRepository {
    private static PostRepositoryJdbcImpl instance;

    private PostRepositoryJdbcImpl() {
        sqlHelper = new SqlHelper(() -> connection);
    }

    public static PostRepositoryJdbcImpl getInstance() {
        if (instance == null) {
            instance = new PostRepositoryJdbcImpl();
        }
        return instance;
    }

    @Override
    public Post save(Post post) {
        if (post.isNew()) {
            sqlHelper.transactionalExecute(conn -> {
                try (PreparedStatement ps = conn.
                        prepareStatement("INSERT INTO posts (content, created, updated, post_status, writer_id) VALUES (?,?,?,?,?)")) {
                    ps.setString(1, post.getContent());
                    ps.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
                    ps.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
                    ps.setString(4, String.valueOf(post.getPostStatus()));
                    ps.setLong(5, post.getWriter().getId());
                    ps.executeUpdate();
                }
                return null;
            });
        } else {
            System.out.println("Post is not new. Cannot be saved.");
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        if (post.isNew()) {
            System.out.println("Post is new, can not be updated.");
            return null;
        } else {
            sqlHelper.transactionalExecute(conn -> {
                try (PreparedStatement ps = conn.prepareStatement("UPDATE posts SET content=?, created=?, updated=?, post_status=? WHERE id=?")) {
                    ps.setString(1, post.getContent());
                    ps.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
                    ps.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
                    ps.setString(4, String.valueOf(post.getPostStatus()));
                    ps.setLong(6, post.getId());
                    ps.executeUpdate();
                }
                return null;
            });
            return post;
        }
    }

    @Override
    public Post getById(Long id) {
        if (id == null) {
            return null;
        }
        List<Label> labelsByPostId = LabelRepositoryJdbcImpl.getInstance().getLabelsByPostId(id);
        return sqlHelper.execute("SELECT * FROM posts WHERE id=?", ps -> {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return getPost(id, labelsByPostId, rs);
        });
    }

    public List<Post> getPostsByWriterId(Long writerId) {
        return sqlHelper.execute("SELECT * FROM posts WHERE writer_id=?", ps -> {
            ps.setLong(1, writerId);
            ResultSet rs = ps.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                Long postId = rs.getLong("id");
                List<Label> labelsByPostId = LabelRepositoryJdbcImpl.getInstance().getLabelsByPostId(postId);
                posts.add(getPost(postId, labelsByPostId, rs));
            }
            return posts;
        });
    }

    @Override
    public void deleteById(Long id) {
        sqlHelper.execute("DELETE FROM posts WHERE id=?", ps -> {
            ps.setLong(1, id);
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM posts", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                List<Label> labelsByPostId = LabelRepositoryJdbcImpl.getInstance().getLabelsByPostId(id);
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
        Writer writer = WriterRepositoryJdbcImpl.getInstance().getById(rs.getLong("writer_id"));
        return new Post(id, content, created, updated, postStatus, labelsByPostId, writer);
    }
}