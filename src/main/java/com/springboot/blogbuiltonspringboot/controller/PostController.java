package com.springboot.blogbuiltonspringboot.controller;

import com.springboot.blogbuiltonspringboot.payloadDTO.PostDTO;
import com.springboot.blogbuiltonspringboot.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller class should be annotated with @rest controller annotation
@RestController
// base url endpoint
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    // generating a class with one constructor can omit @auto wired annotation
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post endpoint
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    // get all posts
    @GetMapping
    public List<PostDTO> getAllPosts(){
        return  postService.getAllPosts();
    }
}
