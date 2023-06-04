package com.example.spring_board.author.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorRequestDto;
import com.example.spring_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("author/login")
    public String authorLogin() {
        return "author/login";
    }

    @GetMapping("authors/new")
    public String authorCreateForm() {
        return "author/author-register";
    }

    @PostMapping("authors/new")
    public String authorCreate(AuthorRequestDto authorRequestDto) throws SQLException {

        // 실무에서는 setter 최대한 배제
        // 이유는 최초 객체 생성시점 뿐만 아니라, 여러군데에서 setter를 통해 객체 값을 변경가능하게 되어,
        // 데이터의 정확성을 보장하기 어렵고, 유지보수가 어렵다

        // 방법1. setter 방식 : 최초시점 이외의 다른 클래스에서 객체 값을 변경함으로써, 유지보수의 어려움 발생
//        author1.setName(authorRequestDto.getName());
//        author1.setEmail(authorRequestDto.getEmail());
//        author1.setPassword(authorRequestDto.getPassword());
//        author1.setRole(authorRequestDto.getRole());
//        author1.setCreateDate(authorRequestDto.now());

        // 방법2. 생성자 방식(setter 배제)
        // 문제점은 반드시 매개변수의 순서를 맞춰줘야 한다는 점이고, 매개변수가 많아지게 되면 개발의 어려움 발생
//        Author author1 = new Author(
//                authorRequestDto.getName(),
//                authorRequestDto.getEmail(),
//                authorRequestDto.getPassword(),
//                authorRequestDto.getRole()
//        );

        // 방법3. builder 패턴 : 매개변수 순서와 상관없이 객체 생성가능
        Author author1 = Author.builder()
                        .password(authorRequestDto.getPassword())
                        .name(authorRequestDto.getName())
                        .email(authorRequestDto.getEmail())
                        .role(authorRequestDto.getRole())
                        .build();
        authorService.create(author1);
        return "redirect:/";
    }

    @GetMapping("authors")
    public String authorFindAll(Model model) throws SQLException {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authorlist", authors);
        return "author/author-list";
    }

    @GetMapping("author")
    public String authorFindById(@RequestParam(value = "id") Long myId, Model model) throws Exception {
        Author author = authorService.findById(myId);
        System.out.println(author.getName());
        System.out.println(author.getEmail());
        model.addAttribute("author", author);
        return "author/author-detail";
    }

    @PostMapping("author/update")
    public String authorupdate(AuthorRequestDto authorRequestDto) throws Exception {
        authorService.update(authorRequestDto);
        return "redirect:/";
    }

    // DeleteMapping을 사용할 수도 있지만, DeleteMapping은 form 태그에서는 지원하지 않는다
    // form태그에서 DeleteMapping을 지원하지 않는다는 얘기는 action에 = "delete"를 줄 수 없다는 뜻
    // 그래서, react나 vue.js와 같은 프론트엔드의 특정한 기술을 통해서 delete 요청을 일반적으로 하므로,
    // rest api 방식의 개발(json)에서는 DeleteMapping이 가능하다
    @GetMapping("author/delete")
    public String deleteAuthor(@RequestParam(value = "id") String id) {
        authorService.delete(Long.parseLong(id));
        return "redirect:/authors";
    }

}
