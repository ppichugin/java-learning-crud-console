package model;

public enum PostStatus {
    ACTIVE("Опубликован"),
    UNDER_REVIEW("На модерации"),
    DELETED("Удален");

    private final String status;

    PostStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
