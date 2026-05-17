package com.vsu.zachet.repository;

import com.vsu.zachet.repository.mapper.UserRowMapper;
import com.vsu.zachet.request.AddUserRequest;
import com.vsu.zachet.request.EditUserRequest;
import com.vsu.zachet.response.UserAuthResponse;
import com.vsu.zachet.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserRepository(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    public UserResponse addUser(AddUserRequest request) {
        return jdbcTemplate.queryForObject("""
                INSERT INTO users (username, email, password_hash)
                VALUES(?,?,?)
                RETURNING id, username, email
                """,
                new Object[] {request.username(), request.email(), passwordEncoder.encode(request.password_hash())},
                userRowMapper);
    }

    public void deleteUser(Long id) {
        jdbcTemplate.update("""
            DELETE FROM users
            WHERE id = ?
            """, id);
    }

    public void editUser(Long id, EditUserRequest request) {
        jdbcTemplate.update("""
            UPDATE users
            SET email = ?, password_hash = ?
            WHERE id = ?
            """,
                request.email(), request.password_hash().hashCode(), id);
    }

    public List<UserResponse> getUsers(int page) {
        return jdbcTemplate.query("""
                SELECT id, username, email
                FROM users
                LIMIT ?
                OFFSET ?
                """,
                new Object[] {5, page * 5},
                userRowMapper);
    }

    public Optional<UserAuthResponse> findAuthUser(String username) {
        return jdbcTemplate.query("""
                        SELECT username, password_hash
                        FROM users
                        WHERE username LIKE ?
                        """,
                        new Object[]{username},
                        (rs, rowNum) -> new UserAuthResponse(rs.getString("username"),
                                rs.getString("password_hash")))
                .stream()
                .findFirst();
    }

    public void lockOnUsername(String username) {
        jdbcTemplate.query("""
                SELECT pg_advisory_xact_lock(hashtext(?))
                """,
                (rs, rowNum) -> { return null;},
                username);
    }

    public Optional<UserResponse> findUser(String username) {
        return jdbcTemplate.query("""
                        SELECT id, username, email
                        FROM users
                        WHERE username LIKE ?
                        """,
                new Object[]{username},
                userRowMapper)
                .stream()
                .findFirst();
    }
}
