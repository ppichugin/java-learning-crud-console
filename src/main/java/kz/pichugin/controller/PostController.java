package kz.pichugin.controller;

import kz.pichugin.model.Post;
import kz.pichugin.model.StorageCrud;
import kz.pichugin.repository.jdbc.PostRepositoryJdbcImpl;
import kz.pichugin.service.PostService;

import java.util.List;

public class PostController implements StorageCrud<Post, Long> {
    private final PostService postService;

    public PostController() {
        postService = new PostService(new PostRepositoryJdbcImpl());
    }

    @Override
    public Post save(Post type) {
        return postService.save(type);
    }

    @Override
    public Post update(Post type) {
        return postService.update(type);
    }

    @Override
    public Post getById(Long aLong) {
        return postService.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        postService.deleteById(aLong);
    }

    @Override
    public List<Post> getAll() {
        return postService.getAll();
    }
}
