package com.vsu.zachet.repository.mapper;

import com.vsu.zachet.response.CommentResponse;
import com.vsu.zachet.response.UserResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CommentRowMapper implements RowMapper<CommentResponse> {
    @Override
    public CommentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CommentResponse(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("post_id"),
                rs.getString("content")
        );
    }
}
