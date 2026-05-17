create table users (
    id bigserial primary key,
    username text unique,
    email text,
    password_hash text
);

create table posts (
    id bigserial primary key,
    user_id bigint references users(id),
    title text,
    content text
);

create table comments (
    id bigserial primary key,
    post_id bigint references posts(id),
    user_id bigint references users(id),
    content text
);