package com.springboot.blogbuiltonspringboot.service.impl;

import com.springboot.blogbuiltonspringboot.entity.Post;
import com.springboot.blogbuiltonspringboot.payloadDTO.PostDTO;
import com.springboot.blogbuiltonspringboot.repository.PostRepository;
import com.springboot.blogbuiltonspringboot.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//annotate with @service annotation for spring service class
@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    // one constructor in the class omit @auto wired annotation
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        // convert DTO to entity & save to the database
        // get the instance passed in the DTO class and store in the database
        // this is because it is better for the client to interact with the DTO than the entity itself.
        // DTO communicates with the entity.

        Post post = mapToEntity(postDTO);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        // This is to send to the client REST API
        // get the instance already saved in the database and send it to the client for viewing either in Postman etc

        PostDTO postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    //convert entity into DTO
    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setTitle(post.getTitle());
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setDescription(post.getDescription());

        return postDTO;
    }

    // convert DTO into entity
    private Post mapToEntity(PostDTO postDTO) {
        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        return post;
    }
}
