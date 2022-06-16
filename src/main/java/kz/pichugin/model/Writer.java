package kz.pichugin.model;

import java.util.List;
import java.util.Objects;

public class Writer {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private List<Post> posts;

    public Writer(String firstName, String lastName) {
        this(null, firstName, lastName, null);
    }

    public Writer(Long id, String firstName, String lastName, List<Post> posts) {
        Objects.requireNonNull(firstName, "first name can not be empty");
        Objects.requireNonNull(lastName, "last name can not be empty");
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Writer{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", posts=").append(posts);
        sb.append('}');
        return sb.toString();
    }
}