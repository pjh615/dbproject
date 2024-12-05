package com.example.dbproject.model.Comments;

import com.example.dbproject.model.Member.Member;
import com.example.dbproject.model.Posts.Posts;
import com.example.dbproject.model.Reply.Reply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comments {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Posts post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Reply> replies;

    @ManyToMany
    Set<Member> liker;
}
