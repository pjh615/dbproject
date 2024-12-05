package com.example.dbproject.service;

import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Member.MemberRepository;
import com.example.dbproject.domain.Member.MemberRole;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository mRepo;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Optional<Member> _member = mRepo.findByMemberId(memberId);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Member member = _member.get();
        String role = member.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(role)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getRole()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getRole()));
        }

        return new User(member.getMemberId(), member.getPassword(), authorities);
    }
}
