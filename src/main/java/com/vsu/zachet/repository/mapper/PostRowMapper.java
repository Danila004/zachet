package com.vsu.zachet.repository.mapper;

import com.vsu.zachet.response.PostResponse;
import com.vsu.zachet.response.UserResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostRowMapper implements RowMapper<PostResponse> {
    @Override
    public PostResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PostResponse(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}
