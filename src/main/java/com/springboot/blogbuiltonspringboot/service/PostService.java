package com.springboot.blogbuiltonspringboot.service;

import com.springboot.blogbuiltonspringboot.payloadDTO.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();
}
