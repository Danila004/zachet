create table users (
    id bigserial primary key,
    username text unique,
    email text,
    passwordHash text
);

create table posts (
    id bigserial primary key,
    userId bigint references users(id),
    title text,
    content text
);

create table comments (
    id bigserial primary key,
    postId bigint references posts(id),
    userId bigint references users(id),
    content text
);