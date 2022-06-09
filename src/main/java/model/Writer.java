package model;

import java.util.List;

public class Writer {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final List<Post> posts;

    public Writer(long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }
}
