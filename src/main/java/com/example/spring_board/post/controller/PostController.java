package com.example.spring_board.post.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.repository.AuthorRepository;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostRequestDto;
import com.example.spring_board.post.service.PostService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("post/new")
    public String postCreateForm() {
        return "post/post-register";
    }

    @PostMapping("post/new")
    public String postCreate(PostRequestDto postRequestDto) throws SQLException {
        postService.create(postRequestDto);
        return "redirect:/";
    }

    @GetMapping("posts")
    public String postFindAll(Model model) throws SQLException {
        List<Post> posts = postService.findAll();
        model.addAttribute("postslist", posts);
        return "post/post-list";
    }

    @GetMapping("post")
    public String postFindById(@RequestParam(value = "id") Long myId, Model model) throws EntityNotFoundException {
        Post post = postService.findById(myId);
        model.addAttribute("post", post);
        return "post/post-detail";
    }

    @PostMapping("post/update")
    public String postupdate(PostRequestDto postRequestDto) throws Exception {
        postService.update(postRequestDto);
        return "redirect:/posts";
    }

    @GetMapping("post/delete")
    public String deletePost(@RequestParam(value = "id") String id) {
        postService.delete(Long.parseLong(id));
        return "redirect:/posts";
    }

}
