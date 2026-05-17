package com.vsu.zachet.repository;

import com.vsu.zachet.repository.mapper.CommentRowMapper;
import com.vsu.zachet.request.AddCommentRequest;
import com.vsu.zachet.request.EditCommentRequest;
import com.vsu.zachet.response.CommentResponse;
import com.vsu.zachet.response.PostResponse;
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
                INSERT INTO comments (post_id, user_id, content)
                VALUES(?,?,?)
                RETURNING id, content, user_id, post_id
                """,
                new Object[] {request.post_id(), request.user_id(), request.content()},
                commentRowMapper);
    }

    public void deleteComment(Long id) {
        jdbcTemplate.update("""
            DELETE FROM comments
            WHERE id = ?
            """, id);
    }

    public void editComment(Long id, EditCommentRequest request) {
        jdbcTemplate.update("""
            UPDATE comments
            SET content = ?
            WHERE id = ?
            """,
                request.content(), id);
    }

    public List<CommentResponse> getComments(int page) {
        return jdbcTemplate.query("""
                SELECT id, post_id, user_id, content
                FROM comments
                LIMIT ?
                OFFSET ?
                """,
                new Object[] {5, page * 5},
                commentRowMapper);
    }

    public Optional<CommentResponse> findComment(Long id) {
        return jdbcTemplate.query("""
                        SELECT id, content, user_id, post_id
                        FROM comments
                        WHERE id = ?
                        """,
                        new Object[]{id},
                        commentRowMapper)
                .stream()
                .findFirst();
    }
}
