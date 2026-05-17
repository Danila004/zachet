package com.vsu.zachet.controller;

import com.vsu.zachet.AbstractIntegrationTest;
import com.vsu.zachet.repository.CommentRepository;
import com.vsu.zachet.repository.PostRepository;
import com.vsu.zachet.repository.UserRepository;
import com.vsu.zachet.request.*;
import com.vsu.zachet.response.CommentResponse;
import com.vsu.zachet.response.PostResponse;
import com.vsu.zachet.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentControllerTest extends AbstractIntegrationTest {
    @Autowired
    private CommentController commentController;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void cleanUsersPostsComments() {
        jdbcTemplate.update("""
            DELETE FROM comments
        """);
        jdbcTemplate.update("""
            DELETE FROM posts
        """);
        jdbcTemplate.update("""
            DELETE FROM users
        """);
    }

    @Test
    public void GivenFiveCommentsInDBWhenGetCommentsThenGetThem() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        PostResponse post = postRepository.addPost(new AddPostRequest(user.id(), "Ha", "ha"));
        for(int i = 0; i < 5; i++) {
            commentRepository.addComment(new AddCommentRequest(user.id(), post.id(), "ha"));
        }

        List<CommentResponse> comments = commentController.getComments(0);

        assertEquals(5, comments.toArray().length);
    }

    @Test
    public void GivenNewCommentWhenAddHimInDBThenSuccessfulAdding() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        PostResponse post = postRepository.addPost(new AddPostRequest(user.id(), "Ha", "ha"));
        AddCommentRequest newComment = new AddCommentRequest(user.id(), post.id(), "ha");

        commentController.addComment(newComment);

        Integer countComments = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM comments
                """,
                Integer.class);
        assertEquals(1, countComments);
    }

    @Test
    public void GivenCommentInDbWhenDeleteHimThenSuccessfulDeleting() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        PostResponse post = postRepository.addPost(new AddPostRequest(user.id(), "Ha", "ha"));
        AddCommentRequest newComment = new AddCommentRequest(user.id(), post.id(), "ha");
        CommentResponse commentInDb = commentRepository.addComment(newComment);

        commentController.deleteComment(commentInDb.id());

        Optional<CommentResponse> newCommentInDB = commentRepository.findComment(commentInDb.id());
        assertTrue(newCommentInDB.isEmpty());
    }

    @Test
    public void GivenCommentInDbWhenEditHimThenSuccessfulChanging() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        PostResponse post = postRepository.addPost(new AddPostRequest(user.id(), "Ha", "ha"));
        AddCommentRequest newComment = new AddCommentRequest(user.id(), post.id(), "ha");
        CommentResponse commentInDb = commentRepository.addComment(newComment);

        EditCommentRequest editComment = new EditCommentRequest("haha");
        commentController.setComment(commentInDb.id(), editComment);

        Optional<CommentResponse> newCommentInDB = commentRepository.findComment(commentInDb.id());
        assertEquals(editComment.content(), newCommentInDB.get().content());
    }
}
