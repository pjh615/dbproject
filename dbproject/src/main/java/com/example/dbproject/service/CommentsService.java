package com.example.dbproject.service;

import com.example.dbproject.exception.DataNotFoundException;
import com.example.dbproject.model.Comments.Comments;
import com.example.dbproject.model.Comments.CommentsRepository;
import com.example.dbproject.model.Member.Member;
import com.example.dbproject.model.Posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository cRepo;

    public Comments getComment(Integer id) {
        Optional<Comments> c = cRepo.findById(id);
        if(c.isPresent()) {
            return c.get();
        } else {
            throw new DataNotFoundException("댓글을 찾을 수 없습니다.");
        }
    }

    public void create(Posts post, String content, Member author) {
        Comments comment = new Comments();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);
        cRepo.save(comment);
    }

    public void update(Comments comment, String content) {
        comment.setContent(content);
        comment.setUpdateDate(LocalDateTime.now());
        cRepo.save(comment);
    }

    public void delete(Comments comment) {
        cRepo.delete(comment);
    }

    public void like(Comments comment, Member liker) {
        comment.getLiker().add(liker);
        cRepo.save(comment);
    }
}
