alter table posts drop constraint posts_userid_fkey;
alter table comments drop constraint comments_userId_fkey;
alter table comments drop constraint comments_postId_fkey;