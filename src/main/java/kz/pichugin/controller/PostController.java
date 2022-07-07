package kz.pichugin.controller;

import kz.pichugin.model.Post;
import kz.pichugin.model.StorageCrud;
import kz.pichugin.model.Writer;
import kz.pichugin.repository.jdbc.PostRepositoryJdbcImpl;
import kz.pichugin.repository.jdbc.WriterRepositoryJdbcImpl;
import kz.pichugin.service.PostService;
import kz.pichugin.service.WriterService;

import java.util.List;

public class PostController implements StorageCrud<Post, Long> {
    private final PostService postService;
    private final WriterService writerService;

    public PostController() {
        postService = new PostService(new PostRepositoryJdbcImpl());
        writerService = new WriterService(new WriterRepositoryJdbcImpl());
    }

    @Override
    public Post save(Post post) {
        if (!post.isNew()) {
            System.out.println("Post is not new. Cannot be saved.");
            return null;
        }
        Long writerId = post.getWriter().getId();
        Writer writer = writerService.getById(writerId);
        if (writer == null) {
            System.out.println("Writer with id " + writerId + " not exist. Post cannot be saved.");
            return null;
        }
        Post savedPost = postService.save(post);
        if (savedPost != null) {
            System.out.println("Saved!");
        }
        return savedPost;
    }

    @Override
    public Post update(Post post) {

        return postService.update(post);
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
