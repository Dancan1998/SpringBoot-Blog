package com.springboot.blogbuiltonspringboot.service;

import com.springboot.blogbuiltonspringboot.payloadDTO.PostDTO;
import com.springboot.blogbuiltonspringboot.payloadDTO.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    void deletePost(long id);
}
