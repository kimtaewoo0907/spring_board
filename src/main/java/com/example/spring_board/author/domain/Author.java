package com.example.spring_board.author.domain;

import com.example.spring_board.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
// 기본생성자를 만들어주는 어노테이션
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 50)
    private String name;

    @Setter
    @Column(length = 50, unique = true)
    private String email;

    @Setter
    @Column(length = 20)
    private String password;

    @Setter
    @Column(length = 10)
    private String role;

    // mysql에서는 datetime 형식으로 column 생성
    @Column
    private LocalDateTime createDate;

    // 생성자 방식과 builder패턴
    @Builder
    public Author(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createDate = LocalDateTime.now();
    }

}
