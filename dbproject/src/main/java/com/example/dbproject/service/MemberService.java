package com.example.dbproject.service;

import com.example.dbproject.DataNotFoundException;
import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository mRepo;
    private final PasswordEncoder passwordEncoder;

    public Member join(String memberId, String nickname, String password) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setNickname(nickname);
        member.setPassword(passwordEncoder.encode(password));
        validDupMem(member);
        mRepo.save(member);
        return member;
    }

    public Member getMember(String memberId) {
        Optional<Member> member = mRepo.findByMemberId(memberId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("Member not found");
        }
    }

    // 아이디, 닉네임 중복 확인 기능
    private void validDupMem(Member member){
        mRepo.findByMemberId(member.getMemberId())
                .ifPresent(m -> {
                            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
                        });
        mRepo.findByNickname(member.getNickname())
                .ifPresent(m -> {
                   throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
                });
    }

}
