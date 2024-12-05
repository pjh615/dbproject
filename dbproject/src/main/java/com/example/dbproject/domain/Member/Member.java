package com.example.dbproject.domain.Member;

import com.example.dbproject.domain.Comments.Comments;
import com.example.dbproject.domain.Posts.Posts;
import com.example.dbproject.domain.Reply.Reply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String memberId;

    @Column(unique = true)
    private String nickname;

    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Posts> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Comments> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Reply> reply;
}
