DELETE
FROM writers;
DELETE
FROM posts;
DELETE
FROM labels;

INSERT INTO writers (first_name, last_name)
VALUES ('John', 'Tolkien'),
       ('Ernest', 'Hemingway'),
       ('Agatha', 'Christie'),
       ('Thomas', 'Cormen'),
       ('Harold', 'Abelson'),
       ('Herbert', 'Schildt');

INSERT INTO posts (content, created, updated, post_status, writer_id)
VALUES ('The hobbit back to home', CURRENT_DATE, NULL, 'ACTIVE', 1),
       ('The hobbit went on a journey', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 YEAR), NULL, 'ACTIVE', 1),
       ('The Old Man and the Sea', CURRENT_TIMESTAMP - 2, NULL, 'ACTIVE', 2),
       ('The Secret of Chimneys', CURRENT_TIMESTAMP - 5, NULL, 'ACTIVE', 3),
       ('The Complete Reference, 12 Edition', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 MONTH), NULL, 'ACTIVE', 6),
       ('The Complete Reference, 11 Edition', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 YEAR), CURRENT_DATE, 'ACTIVE', 6),
       ('Java: A Beginners Guide, 9 Edition', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 MONTH), NULL, 'ACTIVE', 6);

INSERT INTO labels (name, post_id)
VALUES ('Java', 5),
       ('Java', 6),
       ('Classic', 3),
       ('Java', 7),
       ('Must read', 7),
       ('Novices', 7);
