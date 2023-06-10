package com.example.spring_board.post.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.repository.AuthorRepository;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostRequestDto;
import com.example.spring_board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
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
    public String postCreate(PostRequestDto PostRequestDto) throws SQLException {
        System.out.println(PostRequestDto.getApointment());
        System.out.println(PostRequestDto.getApointment_time());
        Author author1 = authorService.findByEmail(PostRequestDto.getEmail());
        Post post1 = Post.builder()
                .title(PostRequestDto.getTitle())
                .contents(PostRequestDto.getContents())
                // post에는 author변수가 있으므로, post생성시 author객체를 넘겨주면,
                // 내부적으로 author객체에서 author_id를 꺼내어 DB에 넣게 된다
                .author(author1)
                .build();
        postService.create(post1);
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
    public String postupdate(PostRequestDto PostRequestDto) throws Exception {
        postService.update(PostRequestDto);
        return "redirect:/posts";
    }

    @GetMapping("post/delete")
    public String deletePost(@RequestParam(value = "id") String id) {
        postService.delete(Long.parseLong(id));
        return "redirect:/posts";
    }

}
