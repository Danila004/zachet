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
    public ResponseEntity<String> setPost(@PathVariable Long id, @RequestBody EditPostRequest request) {
        postService.setPost(id, request);
        return ResponseEntity.ok("Информация обновлена");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Пост удален");
    }

    @GetMapping
    public List<PostResponse> getPosts(@RequestParam int page) {
        return postService.getPosts(page);
    }

    @PostMapping
    public PostResponse addPost(@RequestBody AddPostRequest request) {
        return postService.addPost(request);
    }
}
