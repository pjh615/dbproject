package com.example.dbproject.model.Posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
    Page<Posts> findAll(Pageable pageable);
    Page<Posts> findAll(Specification<Posts> specification, Pageable pageable);
}
