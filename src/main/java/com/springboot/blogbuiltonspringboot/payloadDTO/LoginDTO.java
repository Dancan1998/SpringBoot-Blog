package com.springboot.blogbuiltonspringboot.payloadDTO;

import lombok.Data;

@Data
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
