package com.example.dbproject.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member save(Member member);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByMemberId(String memberId);
    List<Member> findAll();
}
