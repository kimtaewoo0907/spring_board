package com.example.spring_board.post.service;

import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostRequestDto;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostScheduler {

    @Autowired
    private PostRepository postRepository;
    // 스케쥴러는 초,분,시,일,월,주 등 시간 옵션을 줘야
    // 몇 분에 한 번, 몇 시간에 한 번 등 시간을 주기로 돌아간다
    // 일반적으로 cron이란 리눅스/스프링/그 외 각종 프레임워크에서 스케쥴러를 의미한다
    @Scheduled(cron = "0 0/1 * * * *")
    public void postSchedule() {
        for(Post post : postRepository.findByApointment("Y")) {
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(post.getApointment_time()) == true) {
                post.setApointment(null);
                postRepository.save(post);
            }
        }
    }
}
