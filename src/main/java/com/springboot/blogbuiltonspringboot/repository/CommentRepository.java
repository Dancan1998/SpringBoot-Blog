package com.springboot.blogbuiltonspringboot.repository;

import com.springboot.blogbuiltonspringboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
