package com.springboot.blogbuiltonspringboot.service;

import com.springboot.blogbuiltonspringboot.payloadDTO.CommentDTO;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);
}
