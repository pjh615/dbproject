package com.example.dbproject.controller;

import com.example.dbproject.controller.form.MemberForm;
import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService mService;

    @GetMapping("/signup")
    public String signup(MemberForm memberForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        if(!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "password does not match",
                    "비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        try{
            mService.join(memberForm.getMemberId(), memberForm.getNickname(), memberForm.getPassword1());
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
