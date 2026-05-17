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

    public void setComment(Long id, EditCommentRequest request) {
        commentRepository.editComment(id, request);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteComment(id);
    }

    public List<CommentResponse> getComments(int page) {
        return commentRepository.getComments(page);
    }

    public CommentResponse addComment(AddCommentRequest request) {
        return commentRepository.addComment(request);
    }
}
