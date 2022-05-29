package com.springboot.blogbuiltonspringboot.payloadDTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be null or empty")
    private String email;


    @NotEmpty
    @Size(min = 10, message = "the content should not be empty")
    private String body;
}
