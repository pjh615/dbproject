package com.example.dbproject.service;

import com.example.dbproject.exception.DataNotFoundException;
import com.example.dbproject.model.Comments.Comments;
import com.example.dbproject.model.Member.Member;
import com.example.dbproject.model.Posts.Posts;
import com.example.dbproject.model.Posts.PostsRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostsService {

    private final PostsRepository pRepo;
    private final ImagesService iService;

    private Specification<Posts> search(String keyword) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Posts> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);
                Join<Posts, Member> m1 = root.join("author", JoinType.LEFT);
                Join<Posts, Comments> c = root.join("comments", JoinType.LEFT);
                Join<Posts, Member> m2 = root.join("author", JoinType.LEFT);
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),   //title
                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%"), //post content
                        criteriaBuilder.like(m1.get("memberId"), "%" + keyword + "%"),  //author
                        criteriaBuilder.like(m1.get("nickname"), "%" + keyword + "%"), //nickname 12/04add
                        criteriaBuilder.like(c.get("content"), "%" + keyword + "%"),    //comment content
                        criteriaBuilder.like(m2.get("memberId"), "%" + keyword + "%")); //comment author
            }
        };
    }

    public Page<Posts> getAllPosts(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Posts> specification = search(keyword);
        return pRepo.findAll(specification, pageable);
    }

    public Posts getPost(Integer id) {
        Optional<Posts> post = pRepo.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public Integer create(String title, String content, Member member) {
        Posts post = new Posts();
        post.setTitle(title);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(member);
        pRepo.save(post);
        return post.getId();
    }

    public void update(Posts post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
        post.setUpdateDate(LocalDateTime.now());
        pRepo.save(post);
    }

    public void delete(Posts post) {
        pRepo.delete(post);
    }

    public void like(Posts post, Member liker) {
        post.getLiker().add(liker);
        pRepo.save(post);
    }
}
