package com.example.spring_board.author.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
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

    @GetMapping("authors/new")
    public String authorCreateForm() {
        return "author/author-register";
    }

    @PostMapping("authors/new")
    public String authorCreate(@RequestParam(value = "name") String name,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "role") String role) throws SQLException {
        Author author1 = new Author();
        author1.setName(name);
        author1.setEmail(email);
        author1.setPassword(password);
        author1.setRole(role);
        author1.setCreateDate(LocalDateTime.now());
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
    public String authorupdate(@RequestParam(value = "id") String id,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "role") String role) throws Exception {
        Author author = new Author();
        author.setId(Long.parseLong(id));
        author.setName(name);
        author.setEmail(email);
        author.setPassword(password);
        author.setRole(role);
        author.setCreateDate(LocalDateTime.now());
        authorService.update(author);
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
