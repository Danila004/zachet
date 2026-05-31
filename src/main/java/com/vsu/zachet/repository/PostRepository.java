package com.vsu.zachet.repository;

import com.vsu.zachet.repository.mapper.PostRowMapper;
import com.vsu.zachet.repository.mapper.PostRowMapper;
import com.vsu.zachet.request.AddPostRequest;
import com.vsu.zachet.request.EditPostRequest;
import com.vsu.zachet.response.PostResponse;
import com.vsu.zachet.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper postRowMapper;

    public PostRepository(JdbcTemplate jdbcTemplate, PostRowMapper postRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRowMapper = postRowMapper;
    }
    
    public PostResponse addPost(AddPostRequest request) {
        return jdbcTemplate.queryForObject("""
                INSERT INTO posts (userId, title, content)
                VALUES(?,?,?)
                RETURNING id, title, content, userId
                """,
                new Object[] {request.userId(), request.title(), request.content()},
                postRowMapper);
    }

    public PostResponse deletePost(Long id) {
        return jdbcTemplate.queryForObject("""
            DELETE FROM posts
            WHERE id = ?
            RETURNING id, title, content, userId
            """,
                postRowMapper,
                id);
    }

    public PostResponse editPost(Long id, EditPostRequest request) {
        return jdbcTemplate.queryForObject("""
            UPDATE posts
            SET title = ?, content = ?
            WHERE id = ?
            RETURNING id, title, content, userId
            """,
                postRowMapper,
                request.title(), request.content(), id);
    }

    public List<PostResponse> getPosts(int page) {
        return jdbcTemplate.query("""
                SELECT id, title, content, userId
                FROM posts
                LIMIT ?
                OFFSET ?
                """,
                new Object[] {5, page * 5},
                postRowMapper);
    }

    public List<PostResponse> findPostsByTitle(String title, int page) {
        return jdbcTemplate.query("""
                SELECT id, title, content, userId
                FROM posts
                WHERE title LIKE ?
                LIMIT ?
                OFFSET ?
                """,
                new Object[] {"%" + title + "%", 5, page * 5},
                postRowMapper);
    }

    public Optional<PostResponse> findPost(Long id) {
        return jdbcTemplate.query("""
                        SELECT id, title, content, userId
                        FROM posts
                        WHERE id = ?
                        """,
                        new Object[]{id},
                        postRowMapper)
                .stream()
                .findFirst();
    }
}
