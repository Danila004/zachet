package com.vsu.zachet.controller;

import com.vsu.zachet.request.AddPostRequest;
import com.vsu.zachet.request.EditPostRequest;
import com.vsu.zachet.response.PostResponse;
import com.vsu.zachet.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PatchMapping(path = "/{id}")
    public PostResponse setPost(@PathVariable Long id, @RequestBody EditPostRequest request) {
        return postService.setPost(id, request);
    }

    @DeleteMapping(path = "/{id}")
    public PostResponse deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @GetMapping
    public List<PostResponse> getPosts(@RequestParam int page) {
        return postService.getPosts(page);
    }

    @GetMapping(path = "/search")
    public List<PostResponse> findPostsByTitle(@RequestParam String title, @RequestParam int page) {
        return postService.findPostsByTitle(title, page);
    }

    @PostMapping
    public PostResponse addPost(@RequestBody AddPostRequest request) {
        return postService.addPost(request);
    }
}
