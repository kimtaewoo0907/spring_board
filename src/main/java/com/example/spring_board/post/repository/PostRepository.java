package com.example.spring_board.post.repository;

import com.example.spring_board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // jpa repository에서 customizing 메서드 생성
    // 서드명(리턴타입 메매개변수)
    List<Post> findByApointment(String apointment);
}
