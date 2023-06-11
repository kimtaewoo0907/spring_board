package com.example.spring_board.post.service;

import com.example.spring_board.author.controller.AuthorController;
import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostRequestDto;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorService authorService;

    public void create(PostRequestDto postRequestDto) throws SQLException {
        String my_appointment = null;
        LocalDateTime my_appointment_time = null;
        if(postRequestDto.getApointment() != null && !postRequestDto.getApointment_time().isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(postRequestDto.getApointment_time(), dateTimeFormatter);
            LocalDateTime currentTime = LocalDateTime.now();
            if(currentTime.isBefore(localDateTime) == true) {
                my_appointment_time = localDateTime;
                my_appointment = "Y";
            }
        }
        Author author1 = authorService.findByEmail(postRequestDto.getEmail());
        Post post1 = Post.builder()
                .title(postRequestDto.getTitle())
                .contents(postRequestDto.getContents())
                // post에는 author변수가 있으므로, post생성시 author객체를 넘겨주면,
                // 내부적으로 author객체에서 author_id를 꺼내어 DB에 넣게 된다
                .author(author1)
                .apointment(my_appointment)
                .apointment_time(my_appointment_time)
                .build();
        postRepository.save(post1);
    }

    public List<Post> findAll(){
        List<Post> posts = postRepository.findByApointment(null);
        return posts;
    }

    public Post findById(Long myId) throws EntityNotFoundException {
        Post post = postRepository.findById(myId).orElseThrow(EntityNotFoundException::new);
        return post;
    }

    public void update(PostRequestDto postRequestDto) throws Exception {
        Post post1 = postRepository.findById(Long.parseLong(postRequestDto.getId())).orElseThrow(Exception::new);
        if(post1 == null) {
            throw new Exception();
        } else {
            post1.setTitle(postRequestDto.getTitle());
            post1.setContents(postRequestDto.getContents());
            post1.setCreateDate(LocalDateTime.now());
            postRepository.save(post1);
        }
    }

    public void delete(Long id) {
        postRepository.delete(this.findById(id));
    }

}
