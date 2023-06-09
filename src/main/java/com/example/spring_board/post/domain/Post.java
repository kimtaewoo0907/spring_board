package com.example.spring_board.post.domain;

import com.example.spring_board.author.domain.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 50)
    private String title;

    @Setter
    @Column
    private String contents;

    // autohr 테이블의 id를 fk로 잡는다
    // author_id의 type이 Author가 됨에 유의
    // author_id는 author 테이블의 id에 fk가 걸려있음을 Author 객체타입을 지정함으로서 매핑
    // 그리고 Author author 여기서 author라는 변수명은 테이블의 컬럼명이 아니라,
    // 추후에 Post테이블에서 author를 조회할 때 변수명으로 사용되고, 그 관계성을 ManyToOne으로 표현
    // Author테이블 입장에서는 1:N의 관계이고, Post테이블의 입장에서는 여러사람이 한 사람이 여러 개의 글을 쓸 수 있으므로 N:1이다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "author_id")
    private Author author;

    @Setter
    @Column(length = 1)
    private String apointment;

    @Column
    private LocalDateTime apointment_time;

    @Setter
    @Column
    private LocalDateTime createDate;

    // 생성자방식과 빌더방식
    @Builder
    public Post(String title, String contents, String email, Author author, String apointment, LocalDateTime apointment_time) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.apointment = apointment;
        this.apointment_time = apointment_time;
        this.createDate = LocalDateTime.now();
    }
}
