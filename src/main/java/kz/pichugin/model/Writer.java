package kz.pichugin.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Writer {
    private final Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(Long id, String firstName, String lastName) {
        this(id, firstName, lastName, null);
    }

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }


    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public String toString() {
        String postList = (posts != null) ? posts.stream().map(Post::toString).collect(Collectors.joining("\n")) : null;
        return "Writer {" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + ((postList == null) ? "NO" : "\n" + postList) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Writer)) return false;
        Writer writer = (Writer) o;
        return Objects.equals(id, writer.id) && firstName.equals(writer.firstName) && lastName.equals(writer.lastName) && Objects.equals(posts, writer.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts);
    }
}