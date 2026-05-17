package com.vsu.zachet.controller;

import com.vsu.zachet.AbstractIntegrationTest;
import com.vsu.zachet.repository.PostRepository;
import com.vsu.zachet.repository.UserRepository;
import com.vsu.zachet.request.AddPostRequest;
import com.vsu.zachet.request.AddUserRequest;
import com.vsu.zachet.request.EditPostRequest;
import com.vsu.zachet.response.PostResponse;
import com.vsu.zachet.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostControllerTest extends AbstractIntegrationTest {
    @Autowired
    private PostController postController;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanUsersPosts() {
        jdbcTemplate.update("""
            DELETE FROM posts
        """);
        jdbcTemplate.update("""
            DELETE FROM users
        """);
    }

    @Test
    public void GivenFivePostsInDBWhenGetPostsThenGetThem() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        for(int i = 0; i < 5; i++) {
            postRepository.addPost(new AddPostRequest(user.id(), "Ha", "ha"));
        }

        List<PostResponse> posts = postController.getPosts(0);

        assertEquals(5, posts.toArray().length);
    }

    @Test
    public void GivenNewPostWhenAddHimInDBThenSuccessfulAdding() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        AddPostRequest newPost = new AddPostRequest(user.id(), "Ha", "ha");

        postController.addPost(newPost);

        Integer countPosts = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM posts
                """,
                Integer.class);
        assertEquals(1, countPosts);
    }

    @Test
    public void GivenPostInDbWhenDeleteHimThenSuccessfulDeleting() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        AddPostRequest newPost = new AddPostRequest(user.id(), "Ha", "ha");
        PostResponse postInDb = postRepository.addPost(newPost);

        postController.deletePost(postInDb.id());

        Optional<PostResponse> newPostInDB = postRepository.findPost(postInDb.id());
        assertTrue(newPostInDB.isEmpty());
    }

    @Test
    public void GivenPostInDbWhenEditHimThenSuccessfulChanging() {
        UserResponse user = userRepository.addUser(new AddUserRequest("Bob", "bob@mail.ru", "12345"));
        AddPostRequest newPost = new AddPostRequest(user.id(), "Ha", "ha");
        PostResponse postInDb = postRepository.addPost(newPost);

        EditPostRequest editPost = new EditPostRequest("HAHA", "haha");
        postController.setPost(postInDb.id(), editPost);

        Optional<PostResponse> newPostInDB = postRepository.findPost(postInDb.id());
        assertEquals(editPost.title(), newPostInDB.get().title());
        assertEquals(editPost.content(), newPostInDB.get().content());
    }
}
