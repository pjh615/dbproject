package com.example.dbproject.service;

import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

//    @Test
//    public void join(){
//        //given
//        Member member = new Member();
//        member.setNickname("test");
//        member.setPassword("123456");
//        member.setMemberId("test");
//
//        //when
//        Integer saveId = memberService.join(member).getId();
//
//        //then
//        System.out.println(saveId);
//    }
//
//    @Test
//    public void login(){
//        Member member = new Member();
//        member.setMemberId("user1");
//        member.setPassword("1111");
//
//        boolean b = memberService.loginCheck(member.getMemberId(), member.getPassword());
//    }
}