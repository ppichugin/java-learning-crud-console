package kz.pichugin.model;

import java.time.LocalDateTime;
import java.util.List;

import static kz.pichugin.util.ToLocalDateTimeConverter.DATE_TIME_FORMATTER;

public class Post {
    private Long id;
    private final String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Label> labels;
    private PostStatus postStatus;
    private Writer writer;

    public Post(String content, LocalDateTime created, LocalDateTime updated, PostStatus postStatus, List<Label> labels, Writer writer) {
        this(null, content, created, updated, postStatus, labels, writer);
    }

    public Post(Long id, String content, LocalDateTime created, LocalDateTime updated, PostStatus postStatus, List<Label> labels, Writer writer) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.postStatus = postStatus;
        this.labels = labels;
        this.writer = writer;
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

    public void addLabel(Label label) {
        labels.add(label);
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        String updatedDateTime = updated == null ? "" : ", updated=" + DATE_TIME_FORMATTER.format(updated);
        String labelsValue = labels == null ? "" : ", labels=" + labels;
        String sb = "\tPost{" + "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + DATE_TIME_FORMATTER.format(created) +
                updatedDateTime +
                labelsValue +
                ", postStatus=" + postStatus +
                '}';
        return sb;
    }
}