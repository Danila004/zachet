alter table posts
    add constraint posts_userId_fkey_on_delete
        foreign key (userId)
            references users(id)
            on delete cascade;

alter table comments
    add constraint comments_postId_fkey_on_delete
        foreign key (postId)
            references posts(id)
            on delete cascade;

alter table comments
    add constraint comments_userId_fkey_on_delete
        foreign key (userId)
            references users(id)
            on delete set null;