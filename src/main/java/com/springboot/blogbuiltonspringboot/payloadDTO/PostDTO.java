package com.springboot.blogbuiltonspringboot.payloadDTO;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String description;

    // returns comments in the post object -> nesting
    private Set<CommentDTO> comments;
}
