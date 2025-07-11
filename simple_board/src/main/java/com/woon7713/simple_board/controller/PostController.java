package com.woon7713.simple_board.controller;

import com.woon7713.simple_board.dto.CommentDto;
import com.woon7713.simple_board.dto.PostDto;
import com.woon7713.simple_board.model.Comment;
import com.woon7713.simple_board.model.Post;
import com.woon7713.simple_board.model.User;
import com.woon7713.simple_board.repository.CommentRepository;
import com.woon7713.simple_board.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private User currentUser(HttpSession httpSession) {
        return (User) httpSession.getAttribute("user");
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());

        return "post-list";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpSession httpSession) {
        if (currentUser(httpSession) == null) return "redirect:/login";

        model.addAttribute("postDto", new PostDto());

        return "post-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute PostDto postDto,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        if (bindingResult.hasErrors()) return "post-form";

        User user = currentUser(httpSession);
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Integer id,
            Model model,
            HttpSession httpSession
    ) {
        Post post = postRepository.findById(id).orElseThrow();

        model.addAttribute("post", post);
        model.addAttribute("commentDto", new CommentDto());

        return "post-detail";
    }

    @PostMapping("/{postId}/comments")
    public String addComment(
            @PathVariable Integer postId,
            @Valid @ModelAttribute CommentDto commentDto,
            BindingResult bindingResult,
            HttpSession httpSession,
            Model model
            ){

        User user = currentUser(httpSession);
        if (user == null) {
            return "redirect:/login";
        }

        Post post = postRepository.findById(postId).orElseThrow();
        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
        return "post-detail";
        }

        Comment comment = Comment.builder()
                .post(post)
                .author(user)
                .text(commentDto.getText())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return "redirect:/posts/" + postId;
    }


}
