package com.example.dbproject.controller;

import com.example.dbproject.controller.form.CommentsForm;
import com.example.dbproject.model.Comments.Comments;
import com.example.dbproject.model.Member.Member;
import com.example.dbproject.model.Posts.Posts;
import com.example.dbproject.service.CommentsService;
import com.example.dbproject.service.MemberService;
import com.example.dbproject.service.PostsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class CommentsController {

    private final PostsService pService;
    private final CommentsService cService;
    private final MemberService mService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @Valid CommentsForm commentsForm,
                                BindingResult bindingResult, Principal principal) {
        Posts post = pService.getPost(id);
        Member member = mService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "post_detail";
        }
        cService.create(post, commentsForm.getContent(), member);
        return String.format("redirect:/post/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/update/{id}")
    public String updateComment(CommentsForm commentsForm, @PathVariable("id") Integer id, Principal principal) {
        Comments comment = cService.getComment(id);
        if (!comment.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        commentsForm.setContent(comment.getContent());
        return "comment_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/update/{id}")
    public String updateComment(@Valid CommentsForm commentsForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "comment_create";
        }
        Comments comment = cService.getComment(id);
        if (!comment.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        cService.update(comment, commentsForm.getContent());
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/delete/{id}")
    public String deleteComment(Principal principal, @PathVariable("id") Integer id) {
        Comments comment = cService.getComment(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        boolean isBartender = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_BARTENDER"));
        if (!comment.getAuthor().getMemberId().equals(principal.getName()) && !isAdmin && !isBartender) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        cService.delete(comment);
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/like/{id}")
    public String likeComment(Principal principal, @PathVariable("id") Integer id) {
        Comments comment = cService.getComment(id);
        Member liker = mService.getMember(principal.getName());
        cService.like(comment, liker);
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }


}
