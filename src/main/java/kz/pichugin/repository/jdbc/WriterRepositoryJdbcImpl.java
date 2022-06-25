package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Post;
import kz.pichugin.model.PostStatus;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.WriterRepository;
import kz.pichugin.sql.JdbcOperator;
import kz.pichugin.util.ToLocalDateTimeConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryJdbcImpl extends AbstractJdbcRepository implements WriterRepository {
//    private WriterRepositoryJdbcImpl instance;

//    public WriterRepositoryJdbcImpl() {
//        jdbcOperator = new JdbcOperator(() -> ConnectionMySql.get().getSqlConnection());
//    }

//    public static WriterRepositoryJdbcImpl getInstance() {
//        if (instance == null) {
//            jdbcOperator = new JdbcOperator(() -> connection);
//            instance = new WriterRepositoryJdbcImpl();
//        }
//        return instance;
//    }

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
        if (writer == null) {
            return null;
        }
        if (writer.isNew()) {
            System.out.println("Writer is new, can not be updated.");
            return null;
        } else {
            JdbcOperator.execute("UPDATE writers SET first_name=?, last_name=? WHERE id=?", ps -> {
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
        return JdbcOperator.execute("SELECT * FROM writers WHERE id=?", ps -> {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return getWriter(id, rs);
        });
    }

    @Override
    public void deleteById(Long id) {
        JdbcOperator.execute("DELETE FROM writers WHERE id=?", ps -> {
            ps.setLong(1, id);
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public List<Writer> getAll() {

        //TODO change SQL query to alternative to optimize performance

        /*
         * alternative SQL query
         *
         * SELECT * FROM writers
         * LEFT JOIN posts p ON writers.id = p.writer_id
         * LEFT JOIN labels l ON p.id = l.post_id;
         */
        return JdbcOperator.transactionalExecute(conn -> {
            List<Writer> writers = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM writers")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    long writerId = rs.getLong("id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    Writer writer = new Writer(writerId, first_name, last_name, null);
                    List<Post> postList = new ArrayList<>();
                    try (PreparedStatement psPosts = conn.prepareStatement("SELECT * FROM posts WHERE writer_id=?")) {
                        psPosts.setLong(1, writerId);
                        ResultSet rsPosts = psPosts.executeQuery();
                        while (rsPosts.next()) {
                            long postId = rsPosts.getLong("id");
                            String content = rsPosts.getString("content");
                            LocalDateTime created = ToLocalDateTimeConverter.convert(rsPosts.getTimestamp("created"));
                            LocalDateTime updated = ToLocalDateTimeConverter.convert(rsPosts.getTimestamp("updated"));
                            PostStatus postStatus = PostStatus.valueOf(rsPosts.getString("post_status"));
                            postList.add(new Post(postId, content, created, updated, postStatus, null, writer));
                        }
                    }
                    writer.setPosts(postList);
                    writers.add(writer);
                }
            }
            return writers;
        });
    }

    //todo re-check
    private Writer getWriter(Long writerId, ResultSet rs) throws SQLException {
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
//        List<Post> posts = PostRepositoryJdbcImpl.getPostsByWriterId(writerId);
        return new Writer(writerId, first_name, last_name, null);
    }
}