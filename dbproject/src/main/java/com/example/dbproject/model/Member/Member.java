package com.example.dbproject.model.Member;

import com.example.dbproject.model.Comments.Comments;
import com.example.dbproject.model.Posts.Posts;
import com.example.dbproject.model.Reply.Reply;
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

    private String role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Posts> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Comments> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Reply> reply;
}
