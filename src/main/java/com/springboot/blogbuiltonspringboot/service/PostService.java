package com.springboot.blogbuiltonspringboot.service;

import com.springboot.blogbuiltonspringboot.payloadDTO.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
}
