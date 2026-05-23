package com.vsu.zachet.service;

import com.vsu.zachet.repository.CommentRepository;
import com.vsu.zachet.request.AddCommentRequest;
import com.vsu.zachet.request.EditCommentRequest;
import com.vsu.zachet.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponse setComment(Long id, EditCommentRequest request) {
        return commentRepository.editComment(id, request);
    }

    public CommentResponse deleteComment(Long id) {
        return commentRepository.deleteComment(id);
    }

    public List<CommentResponse> getComments(int page) {
        return commentRepository.getComments(page);
    }

    public CommentResponse addComment(AddCommentRequest request) {
        return commentRepository.addComment(request);
    }
}
