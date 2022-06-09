package model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private final long id;
    private final String content;
    private final LocalDateTime created;
    private final LocalDateTime updated;
    private final List<Label> labels;
    private final PostStatus status;

    public Post(long id, String content, LocalDateTime created, LocalDateTime updated, List<Label> labels, PostStatus status) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.status = status;
    }


}
