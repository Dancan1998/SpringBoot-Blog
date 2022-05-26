package com.springboot.blogbuiltonspringboot.service.impl;

import com.springboot.blogbuiltonspringboot.entity.Post;
import com.springboot.blogbuiltonspringboot.exception.ResourceNotFoundException;
import com.springboot.blogbuiltonspringboot.payloadDTO.PostDTO;
import com.springboot.blogbuiltonspringboot.payloadDTO.PostResponse;
import com.springboot.blogbuiltonspringboot.repository.PostRepository;
import com.springboot.blogbuiltonspringboot.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public PostResponse getAllPosts(int pageNo, int pageSize) {
        // create a pageable instance
        PageRequest pageable = PageRequest.of(pageNo, pageSize);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for the page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        Post updatedPost = postRepository.save(post);

        // convert to DTO and return to the client
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
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
