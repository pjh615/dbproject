package com.example.dbproject.service;

import com.example.dbproject.DataNotFoundException;
import com.example.dbproject.domain.Comments.Comments;
import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Posts.Posts;
import com.example.dbproject.domain.Reply.Reply;
import com.example.dbproject.domain.Reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository rRepo;

    public Reply getReply(Integer id) {
        Optional<Reply> r = rRepo.findById(id);
        if (r.isPresent()) {
            return r.get();
        } else {
            throw new DataNotFoundException("대댓글을 찾을 수 없습니다.");
        }

    }

    public void create(Comments comment, String content, Member author) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setAuthor(author);
        reply.setComment(comment);
        reply.setCreateDate(LocalDateTime.now());
        rRepo.save(reply);
    }

    public void update(Reply reply, String content) {
        reply.setContent(content);
        reply.setUpdateDate(LocalDateTime.now());
        rRepo.save(reply);
    }

    public void delete(Reply reply) {
        rRepo.delete(reply);
    }

    public void like(Reply reply, Member liker) {
        reply.getLiker().add(liker);
        rRepo.save(reply);
    }
}
