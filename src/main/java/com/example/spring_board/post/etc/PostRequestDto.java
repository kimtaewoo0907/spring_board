package com.example.spring_board.post.etc;

import com.example.spring_board.author.domain.Author;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostRequestDto {
    private String id;
    private String title;
    private String contents;
    private String email;
    private String apointment;
    private LocalDateTime apointment_time;
}
