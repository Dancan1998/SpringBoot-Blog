package com.springboot.blogbuiltonspringboot.service.impl;

import com.springboot.blogbuiltonspringboot.entity.Comment;
import com.springboot.blogbuiltonspringboot.entity.Post;
import com.springboot.blogbuiltonspringboot.exception.ResourceNotFoundException;
import com.springboot.blogbuiltonspringboot.payloadDTO.CommentDTO;
import com.springboot.blogbuiltonspringboot.repository.CommentRepository;
import com.springboot.blogbuiltonspringboot.repository.PostRepository;
import com.springboot.blogbuiltonspringboot.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        // convert DTO to entity
        Comment comment = mapToEntity(commentDTO);

        // find post by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

        // once found a  post, set the post to the comment entity
        comment.setPost(post);

        // save comment entity to the DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        // retrieve comments by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert list of comment entities to comment DTOs
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    // map entity to DTo
    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setBody(comment.getBody());
        commentDTO.setEmail(comment.getEmail());

        return commentDTO;
    }

    // map DTO to entity
    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = new Comment();

        comment.setId(commentDTO.getId());
        comment.setBody(commentDTO.getBody());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());

        return comment;
    }
}
