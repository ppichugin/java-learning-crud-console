package kz.pichugin.controller;

import kz.pichugin.model.Post;
import kz.pichugin.service.PostService;

import java.util.List;

public class PostController {
    PostService postService = new PostService();

    public Post save(Post type) {
        return postService.save(type);
    }

    public Post update(Post type) {
        return postService.update(type);
    }

    public Post getById(Long aLong) {
        return postService.getById(aLong);
    }

    public void deleteById(Long aLong) {
        postService.deleteById(aLong);
    }

    public List<Post> getAll() {
        return postService.getAll();
    }
}