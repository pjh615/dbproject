package com.example.dbproject.controller;

import com.example.dbproject.controller.form.CommentsForm;
import com.example.dbproject.controller.form.PostsForm;
import com.example.dbproject.model.Member.Member;
import com.example.dbproject.model.Posts.Posts;
import com.example.dbproject.service.ImagesService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService pService;
    private final MemberService mService;
    private final ImagesService iService;

    @GetMapping("/post/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentsForm commentsForm) {
        Posts post = pService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/create")
    public String createPost(PostsForm postsForm) {
        return "post_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/create")
    public String createPost(@Valid @ModelAttribute PostsForm postsForm, @Valid List<MultipartFile> images, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "post_create";
        }
        Member member = mService.getMember(principal.getName());
        Integer postId = pService.create(postsForm.getTitle(), postsForm.getContent(), member);
        Posts post = pService.getPost(postId);
        iService.upload(post, images);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/update/{id}")
    public String updatePost(PostsForm postsForm, @PathVariable("id") Integer id, Principal principal) {
        Posts post = pService.getPost(id);
        if(!post.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        postsForm.setTitle(post.getTitle());
        postsForm.setContent(post.getContent());
        return "post_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/update/{id}")
    public String updatePost(@Valid PostsForm postsForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if(bindingResult.hasErrors()) {
            return "post_create";
        }
        Posts post = pService.getPost(id);
        if(!post.getAuthor().getMemberId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        pService.update(post, postsForm.getTitle(), postsForm.getContent());
        return String.format("redirect:/post/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/delete/{id}")
    public String deletePost(Principal principal, @PathVariable("id") Integer id) {
        Posts post = pService.getPost(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if(!post.getAuthor().getMemberId().equals(principal.getName()) && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        pService.delete(post);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/like/{id}")
    public String likePost(Principal principal, @PathVariable("id") Integer id) {
        Posts post = pService.getPost(id);
        Member liker = mService.getMember(principal.getName());
        pService.like(post, liker);
        return String.format("redirect:/post/detail/%s", id);
    }
}
