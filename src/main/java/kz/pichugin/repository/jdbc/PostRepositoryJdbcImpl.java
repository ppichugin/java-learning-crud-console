package kz.pichugin.repository.jdbc;

import kz.pichugin.model.Post;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.PostRepository;
import kz.pichugin.repository.WriterRepository;
import kz.pichugin.sql.SqlHelper;

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
}