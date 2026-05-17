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
    public ResponseEntity<String> setComment(@PathVariable Long id, @RequestBody EditCommentRequest request) {
        commentService.setComment(id, request);
        return ResponseEntity.ok("Информация обновлена");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Коментарий удален");
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
