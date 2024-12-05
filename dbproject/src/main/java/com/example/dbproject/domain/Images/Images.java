package com.example.dbproject.domain.Images;


import com.example.dbproject.domain.Posts.Posts;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imagePath;

    private LocalDateTime createDate;

    @ManyToOne
    private Posts post;

    public String getImagePath() {
        System.out.println(imagePath);
        return imagePath;
    }
}
