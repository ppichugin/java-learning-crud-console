package kz.pichugin.service;

import kz.pichugin.model.Post;
import kz.pichugin.model.StorageCrud;
import kz.pichugin.repository.PostRepository;

import java.util.List;

public class PostService implements StorageCrud<Post, Long> {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post save(Post type) {
        return repository.save(type);
    }

    @Override
    public Post update(Post type) {
        return repository.update(type);
    }

    @Override
    public Post getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public List<Post> getAll() {
        return repository.getAll();
    }
}