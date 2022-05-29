package com.springboot.blogbuiltonspringboot.payloadDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;

    // title should not be null or empty
    // title must have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    // returns comments in the post object -> nesting
    private Set<CommentDTO> comments;
}
