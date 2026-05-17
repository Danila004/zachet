package com.vsu.zachet.service;

import com.vsu.zachet.repository.PostRepository;
import com.vsu.zachet.request.AddPostRequest;
import com.vsu.zachet.request.EditPostRequest;
import com.vsu.zachet.response.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void setPost(Long id, EditPostRequest request) {
        postRepository.editPost(id, request);
    }

    public void deletePost(Long id) {
        postRepository.deletePost(id);
    }

    public List<PostResponse> getPosts(int page) {
        return postRepository.getPosts(page);
    }

    public PostResponse addPost(AddPostRequest request) {
        return postRepository.addPost(request);
    }
}
