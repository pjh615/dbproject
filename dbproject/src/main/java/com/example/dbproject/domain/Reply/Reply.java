package com.example.dbproject.domain.Reply;

import com.example.dbproject.domain.Comments.Comments;
import com.example.dbproject.domain.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Comments comment;

    @ManyToMany
    Set<Member> liker;
}
