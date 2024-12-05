package com.example.dbproject.domain.Posts;

import com.example.dbproject.domain.Images.Images;
import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Comments.Comments;
import com.example.dbproject.domain.Reply.Reply;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Posts {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ManyToOne
    private Member author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comments> comments;

    @ManyToMany
    Set<Member> liker;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Images> images;
}
