package com.springboot.blogbuiltonspringboot.service.impl;

import com.springboot.blogbuiltonspringboot.entity.Comment;
import com.springboot.blogbuiltonspringboot.entity.Post;
import com.springboot.blogbuiltonspringboot.exception.BlogAPIException;
import com.springboot.blogbuiltonspringboot.exception.ResourceNotFoundException;
import com.springboot.blogbuiltonspringboot.payloadDTO.CommentDTO;
import com.springboot.blogbuiltonspringboot.repository.CommentRepository;
import com.springboot.blogbuiltonspringboot.repository.PostRepository;
import com.springboot.blogbuiltonspringboot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        // convert DTO to entity
        Comment comment = mapToEntity(commentDTO);

        // find post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

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

    // find comment in the DB with the give post id
    // no post id -> throw error
    // no comment with id -> throw error
    private Comment comment(long postId, long commentId) {
        // find post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // find comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        return comment;
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Comment comment = comment(postId, commentId);

        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentRequest) {
        Comment comment = comment(postId, commentId);

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updated = commentRepository.save(comment);

        return mapToDTO(updated);
    }


    @Override
    public void deleteComment(long postId, long commentId) {
        Comment deleteComment = comment(postId, commentId);

        commentRepository.delete(deleteComment);
    }

    // map entity to DTo
    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
        return commentDTO;
//        CommentDTO commentDTO = new CommentDTO();
//
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setEmail(comment.getEmail());


    }

    // map DTO to entity
    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = mapper.map(commentDTO, Comment.class);
        return comment;
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setBody(commentDTO.getBody());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
    }
}
