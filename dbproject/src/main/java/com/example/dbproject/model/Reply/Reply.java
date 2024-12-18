package com.example.dbproject.model.Reply;

import com.example.dbproject.model.Comments.Comments;
import com.example.dbproject.model.Member.Member;
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
