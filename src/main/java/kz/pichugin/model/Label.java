package kz.pichugin.model;

public class Label {
    private final Long id;
    private final String name;
    private Long postId;

    public Label(Long id, String name) {
        this(id, name, null);
    }

    public Label(String name, Long postId) {
        this(null, name, postId);
    }

    public Label(Long id, String name, Long postId) {
        this.id = id;
        this.name = name;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "(id=" + id + ", '" + name + '\'' + ')';
    }
}