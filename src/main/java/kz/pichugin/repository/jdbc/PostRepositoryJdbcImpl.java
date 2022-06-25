package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Post;
import kz.pichugin.repository.PostRepository;

import java.util.List;

public class PostRepositoryJdbcImpl extends AbstractJdbcRepository implements PostRepository {
    @Override
    public Post save(Post type) {
        return null;
    }

    @Override
    public Post update(Post type) {
        return null;
    }

    @Override
    public Post getById(Long aLong) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Post> getAll() {
        return null;
    }
//    private static PostRepositoryJdbcImpl instance;
//    private WriterRepositoryJdbcImpl writerRepositoryJdbc = new WriterRepositoryJdbcImpl();
//
//    private PostRepositoryJdbcImpl() {
//        jdbcOperator = new JdbcOperator(() -> connection);
//    }
//
//    public static PostRepositoryJdbcImpl getInstance() {
//        if (instance == null) {
//            instance = new PostRepositoryJdbcImpl();
//        }
//        return instance;
//    }

//    @Override
//    public Post save(Post post) {
//        if (post.isNew()) {
//            jdbcOperator.transactionalExecute(conn -> {
//                try (PreparedStatement ps = conn.
//                        prepareStatement("INSERT INTO posts (content, created, updated, post_status, writer_id) VALUES (?,?,?,?,?)")) {
//                    ps.setString(1, post.getContent());
//                    ps.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
//                    ps.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
//                    ps.setString(4, String.valueOf(post.getPostStatus()));
//                    ps.setLong(5, post.getWriter().getId());
//                    ps.executeUpdate();
//                }
//                return null;
//            });
//        } else {
//            System.out.println("Post is not new. Cannot be saved.");
//        }
//        return post;
//    }
//
//    @Override
//    public Post update(Post post) {
//        if (post.isNew()) {
//            System.out.println("Post is new, can not be updated.");
//            return null;
//        } else {
//            jdbcOperator.transactionalExecute(conn -> {
//                try (PreparedStatement ps = conn.prepareStatement("UPDATE posts SET content=?, created=?, updated=?, post_status=? WHERE id=?")) {
//                    ps.setString(1, post.getContent());
//                    ps.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
//                    ps.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
//                    ps.setString(4, String.valueOf(post.getPostStatus()));
//                    ps.setLong(6, post.getId());
//                    ps.executeUpdate();
//                }
//                return null;
//            });
//            return post;
//        }
//    }
//
//    @Override
//    public Post getById(Long id) {
//        if (id == null) {
//            return null;
//        }
//        List<Label> labelsByPostId = LabelRepositoryJdbcImpl.getInstance().getLabelsByPostId(id);
//        return jdbcOperator.execute("SELECT * FROM posts WHERE id=?", ps -> {
//            ps.setLong(1, id);
//            ResultSet rs = ps.executeQuery();
//            return getPost(id, labelsByPostId, rs);
//        });
//    }
//
//    public List<Post> getPostsByWriterId(Long writerId) {
//        return jdbcOperator.execute("SELECT * FROM posts WHERE writer_id=?", ps -> {
//            ps.setLong(1, writerId);
//            ResultSet rs = ps.executeQuery();
//            List<Post> posts = new ArrayList<>();
//            while (rs.next()) {
//                Long postId = rs.getLong("id");
//                List<Label> labelsByPostId = LabelRepositoryJdbcImpl.getInstance().getLabelsByPostId(postId);
//                posts.add(getPost(postId, labelsByPostId, rs));
//            }
//            return posts;
//        });
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        jdbcOperator.execute("DELETE FROM posts WHERE id=?", ps -> {
//            ps.setLong(1, id);
//            ps.executeUpdate();
//            return null;
//        });
//    }
//
//    @Override
//    public List<Post> getAll() {
//        List<Post> postList = new ArrayList<>();
//        jdbcOperator.execute("SELECT * FROM posts", ps -> {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Long id = rs.getLong("id");
//                List<Label> labelsByPostId = LabelRepositoryJdbcImpl.getInstance().getLabelsByPostId(id);
//                postList.add(getPost(id, labelsByPostId, rs));
//            }
//            return null;
//        });
//        return postList;
//    }
//
//    private Post getPost(Long id, List<Label> labelsByPostId, ResultSet rs) throws SQLException {
//        String content = rs.getString("content");
//        LocalDateTime created = ToLocalDateTimeConverter.convert(rs.getTimestamp("created"));
//        LocalDateTime updated = ToLocalDateTimeConverter.convert(rs.getTimestamp("updated"));
//        PostStatus postStatus = PostStatus.valueOf(rs.getString("post_status"));
//        Writer writer = writerRepositoryJdbc.getById(rs.getLong("writer_id"));
//        return new Post(id, content, created, updated, postStatus, labelsByPostId, writer);
//    }
}