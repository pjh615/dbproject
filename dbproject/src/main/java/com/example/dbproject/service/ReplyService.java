package com.example.dbproject.service;

import com.example.dbproject.exception.DataNotFoundException;
import com.example.dbproject.model.Comments.Comments;
import com.example.dbproject.model.Member.Member;
import com.example.dbproject.model.Reply.Reply;
import com.example.dbproject.model.Reply.ReplyRepository;
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
