package kz.pichugin.service;

import kz.pichugin.model.Post;
import kz.pichugin.repository.PostRepository;
import kz.pichugin.repository.jdbc.PostRepositoryJdbcImpl;

import java.util.List;

public class PostService {
    PostRepository repository = PostRepositoryJdbcImpl.getInstance();

    public void setPostRepository(PostRepository repository) {
        this.repository = repository;
    }

    public Post save(Post type) {
        return repository.save(type);
    }

    public Post update(Post type) {
        return repository.update(type);
    }

    public Post getById(Long aLong) {
        return repository.getById(aLong);
    }

    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    public List<Post> getAll() {
        return repository.getAll();
    }

}
