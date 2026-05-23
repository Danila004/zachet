package com.vsu.zachet.controller;

import com.vsu.zachet.request.AddCommentRequest;
import com.vsu.zachet.request.EditCommentRequest;
import com.vsu.zachet.response.CommentResponse;
import com.vsu.zachet.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PatchMapping(path = "/{id}")
    public CommentResponse setComment(@PathVariable Long id, @RequestBody EditCommentRequest request) {
        return commentService.setComment(id, request);
    }

    @DeleteMapping(path = "/{id}")
    public CommentResponse deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @GetMapping
    public List<CommentResponse> getComments(@RequestParam int page) {
        return commentService.getComments(page);
    }

    @PostMapping
    public CommentResponse addComment(@RequestBody AddCommentRequest request) {
        return commentService.addComment(request);
    }
}
