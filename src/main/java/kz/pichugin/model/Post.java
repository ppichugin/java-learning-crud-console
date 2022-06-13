package kz.pichugin.model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private Long id;
    private final String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Label> labels;
    private PostStatus postStatus;

    public Post(Long id, String content, LocalDateTime created, LocalDateTime updated, PostStatus postStatus, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.postStatus = postStatus;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }
}