CREATE TABLE labels
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    post_id BIGINT UNSIGNED
)
    ENGINE = InnoDB;

CREATE TABLE posts
(
    id          SERIAL PRIMARY KEY,
    content     VARCHAR(255) NOT NULL,
    created     DATE         NOT NULL,
    updated     DATE         NOT NULL,
    post_status VARCHAR(255) NOT NULL,
    writer_id   BIGINT UNSIGNED
)
    ENGINE = InnoDB;

CREATE TABLE writers
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL
)
    ENGINE = InnoDB;

# CREATE TABLE post_labels
# (
#     id        SERIAL PRIMARY KEY,
#     post_id   BIGINT UNSIGNED,
#     labels_id BIGINT UNSIGNED,
#     FOREIGN KEY (post_id) REFERENCES posts (id),
#     FOREIGN KEY (labels_id) REFERENCES labels (id)
# )
#     ENGINE = InnoDB;

ALTER TABLE labels
    ADD CONSTRAINT post_id_fk FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE;
ALTER TABLE posts
    ADD CONSTRAINT writer_id_fk FOREIGN KEY (writer_id) REFERENCES writers (id) ON DELETE CASCADE;
