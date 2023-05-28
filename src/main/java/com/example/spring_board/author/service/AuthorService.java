package com.example.spring_board.author.service;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public void create(Author author) throws SQLException {
        authorRepository.save(author);
    }

    public List<Author> findAll() throws SQLException {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Author findById(Long myId) throws EntityNotFoundException {
        Author author = authorRepository.findById(myId).orElseThrow(EntityNotFoundException::new);
        return author;
    }

    public void update(Author author) throws Exception {
        Author author1 = authorRepository.findById(author.getId()).orElse(null);
        if(author1 == null) {
            throw new Exception();
        } else {
            author1.setName(author.getName());
            author1.setEmail(author.getEmail());
            author1.setPassword(author.getPassword());
            authorRepository.save(author);
        }
    }

    public void delete(Long id) {
        // 먼저 db에서 조회 후에 author 객체를 가져온다
        // 해당 author 객체로 jpa가 delete 할 수 있도록 전달해준다
        authorRepository.delete(this.findById(id));
    }

}

