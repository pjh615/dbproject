package com.example.dbproject.controller;

import com.example.dbproject.model.Posts.Posts;
import com.example.dbproject.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostsService pService;

    @GetMapping("/")
    public String homepage(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Posts> paging = pService.getAllPosts(page, keyword);
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        return "homepage";
    }
}
