package com.vsu.zachet.repository;

import com.vsu.zachet.repository.mapper.CommentRowMapper;
import com.vsu.zachet.request.AddCommentRequest;
import com.vsu.zachet.request.EditCommentRequest;
import com.vsu.zachet.response.CommentResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CommentRowMapper commentRowMapper;

    public CommentRepository(JdbcTemplate jdbcTemplate, CommentRowMapper commentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentRowMapper = commentRowMapper;
    }


    public CommentResponse addComment(AddCommentRequest request) {
        return jdbcTemplate.queryForObject("""
                INSERT INTO comments (postId, userId, content)
                VALUES(?,?,?)
                RETURNING id, content, userId, postId
                """,
                new Object[] {request.postId(), request.userId(), request.content()},
                commentRowMapper);
    }

    public CommentResponse deleteComment(Long id) {
        return jdbcTemplate.queryForObject("""
            DELETE FROM comments
            WHERE id = ?
            RETURNING id, content, userId, postId
            """,
                commentRowMapper,
                id);
    }

    public CommentResponse editComment(Long id, EditCommentRequest request) {
        return jdbcTemplate.queryForObject("""
            UPDATE comments
            SET content = ?
            WHERE id = ?
            RETURNING id, content, userId, postId
            """,
                commentRowMapper,
                request.content(), id);
    }

    public List<CommentResponse> getComments(int page) {
        return jdbcTemplate.query("""
                SELECT id, postId, userId, content
                FROM comments
                LIMIT ?
                OFFSET ?
                """,
                new Object[] {5, page * 5},
                commentRowMapper);
    }

    public Optional<CommentResponse> findComment(Long id) {
        return jdbcTemplate.query("""
                        SELECT id, content, userId, postId
                        FROM comments
                        WHERE id = ?
                        """,
                        new Object[]{id},
                        commentRowMapper)
                .stream()
                .findFirst();
    }
}
