package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Label;
import kz.pichugin.model.Post;
import kz.pichugin.model.PostStatus;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;
import kz.pichugin.sql.JdbcOperator;
import kz.pichugin.util.CheckCommand;
import kz.pichugin.util.ToLocalDateTimeConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriterRepositoryJdbcImpl extends AbstractJdbcRepository implements WriterRepository {

    @Override
    public Writer save(Writer writer) {
        if (writer == null) {
            return null;
        }
        if (writer.isNew()) {
            JdbcOperator.execute("INSERT INTO writers (first_name, last_name) VALUE (?,?)", ps -> {
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
        Integer updatedRows = JdbcOperator.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE writers SET first_name=?, last_name=? WHERE id=?")) {
                ps.setString(1, writer.getFirstName());
                ps.setString(2, writer.getLastName());
                ps.setLong(3, writer.getId());
                return ps.executeUpdate();
            }
        });
        return updatedRows > 0 ? writer : null;
    }

    @Override
    public Writer getById(Long id) {
        return JdbcOperator.execute("SELECT * FROM writers WHERE id=?", ps -> {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return getWriter(id, rs);
        });
    }

    @Override
    public void deleteById(Long id) {
        JdbcOperator.execute("DELETE FROM writers WHERE id=?", ps -> {
            ps.setLong(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("Writer deleted");
            } else {
                System.out.println(CheckCommand.ERR_ID);
            }
            return null;
        });
    }

    @Override
    public List<Writer> getAll() {
        return JdbcOperator.transactionalExecute(conn -> {
            Map<Long, Writer> writersMap = new HashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM writers " +
                    "LEFT JOIN posts post ON writers.id = post.writer_id " +
                    "LEFT JOIN labels lbl ON post.id = lbl.post_id " +
                    "ORDER BY writers.id;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    //WRITERS
                    long writerId = rs.getLong("id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    Writer writer = new Writer(writerId, first_name, last_name, null);
                    writersMap.putIfAbsent(writerId, writer);

                    //POSTS
                    String postIdStr = rs.getString("post.id");
                    if (postIdStr == null) {
                        continue;
                    }
                    long postId = Long.parseLong(postIdStr);
                    long postWriterId = rs.getLong("writer_id");
                    Writer writerRelatedToPost = writersMap.get(postWriterId);
                    List<Post> postsFromMap = writerRelatedToPost.getPosts();
                    Post postFromMapPerWriter = null;
                    if (postsFromMap != null) {
                        postFromMapPerWriter = postsFromMap.stream().parallel()
                                .filter(postFromMap -> postFromMap.getId() == postId)
                                .findAny().orElse(null);
                    }
                    if (postFromMapPerWriter == null) {
                        String content = rs.getString("content");
                        LocalDateTime created = ToLocalDateTimeConverter.convert(rs.getTimestamp("created"));
                        LocalDateTime updated = ToLocalDateTimeConverter.convert(rs.getTimestamp("updated"));
                        String postSt = rs.getString("post_status");
                        PostStatus postStatus = postSt != null ? PostStatus.valueOf(postSt) : null;
                        Post post = new Post(postId, content, created, updated, postStatus, null, writerRelatedToPost);

                        if (writerRelatedToPost.getPosts() == null) {
                            writerRelatedToPost.setPosts(new ArrayList<>());
                        }
                        writerRelatedToPost.addPost(post);
                    }

                    //LABELS
                    String labelIdStr = rs.getString("lbl.id");
                    if (labelIdStr == null) {
                        continue;
                    }
                    long labelId = Long.parseLong(labelIdStr);
                    String labelName = rs.getString("name");
                    long labelPostId = rs.getLong("post_id");
                    Label label = new Label(labelId, labelName, labelPostId);
                    writersMap.get(writerId).getPosts().stream().parallel()
                            .filter((Post p) -> p.getId() == labelPostId)
                            .findFirst()
                            .ifPresent(post -> {
                                if (post.getLabels() == null) {
                                    post.setLabels(new ArrayList<>());
                                }
                                post.addLabel(label);
                            });
                }
            }
            return writersMap.values().stream().toList();
        });
    }

    //todo List<Post> posts
    private Writer getWriter(Long writerId, ResultSet rs) throws SQLException {
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
//        List<Post> posts = PostRepositoryJdbcImpl.getPostsByWriterId(writerId);
        return new Writer(writerId, first_name, last_name, null);
    }
}