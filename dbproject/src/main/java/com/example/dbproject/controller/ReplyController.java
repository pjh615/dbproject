package com.example.dbproject.controller;

import com.example.dbproject.controller.form.CommentsForm;
import com.example.dbproject.controller.form.ReplyForm;
import com.example.dbproject.domain.Comments.Comments;
import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Posts.Posts;
import com.example.dbproject.domain.Reply.Reply;
import com.example.dbproject.domain.Reply.ReplyRepository;
import com.example.dbproject.service.CommentsService;
import com.example.dbproject.service.MemberService;
import com.example.dbproject.service.PostsService;
import com.example.dbproject.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService rService;
    private final CommentsService cService;
    private final PostsService pService;
    private final MemberService mService;

    @PostMapping("/reply/create/{commentId}")
    public String createReply(Model model, @PathVariable("commentId") Integer commentId,
                              @Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal) {
        Comments comment = cService.getComment(commentId);
        Member member = mService.getMember(principal.getName());
        Integer postId = comment.getPost().getId();
        if (bindingResult.hasErrors()) {
            Posts post = pService.getPost(postId);
            model.addAttribute("post", post);
            return "post_detail";
        }
        rService.create(comment, replyForm.getContent(), member);

        return String.format("redirect:/post/detail/%s", postId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/update/{id}")
    public String updateReply(ReplyForm replyForm, @PathVariable("id") Integer id, Principal principal) {
        Reply reply = rService.getReply(id);
        if (!reply.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        replyForm.setContent(reply.getContent());
        return "reply_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/update/{id}")
    public String updateReply(@Valid ReplyForm replyForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "reply_create";
        }
        Reply reply = rService.getReply(id);
        if (!reply.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        rService.update(reply, replyForm.getContent());
        Integer postId = reply.getComment().getPost().getId();
        return String.format("redirect:/post/detail/%s", postId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/delete/{id}")
    public String deleteReply(Principal principal, @PathVariable("id") Integer id) {
        Reply reply = rService.getReply(id);
        if (!reply.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        rService.delete(reply);
        return String.format("redirect:/post/detail/%s", reply.getComment().getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/like/{id}")
    public String likeReply(Principal principal, @PathVariable("id") Integer id) {
        Reply reply = rService.getReply(id);
        Member liker = mService.getMember(principal.getName());
        rService.like(reply, liker);
        return String.format("redirect:/post/detail/%s", reply.getComment().getPost().getId());
    }
}
