CREATE TABLE Labels
(
    ID     BIGINT AUTO_INCREMENT PRIMARY KEY,
    Name   VARCHAR(255) NOT NULL,
    PostId BIGINT
);

CREATE TABLE Posts
(
    ID         BIGINT AUTO_INCREMENT,
    Content    VARCHAR(255) NOT NULL,
    Created    DATE         NOT NULL,
    Updated    DATE         NOT NULL,
    PostStatus varchar(255) not null,
    WriterId   BIGINT,
    PRIMARY KEY (ID)
);

CREATE TABLE Writers
(
    ID        BIGINT       NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(255) NOT NULL,
    LastName  varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);
