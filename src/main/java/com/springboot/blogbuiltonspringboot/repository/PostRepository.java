package com.springboot.blogbuiltonspringboot.repository;

import com.springboot.blogbuiltonspringboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
