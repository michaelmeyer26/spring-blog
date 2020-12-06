package com.blog.blog.controllers;

import com.blog.blog.models.Post;
import com.blog.blog.repos.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String postsIndex(Model viewModel) {
        viewModel.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postId(@PathVariable int id, Model model) {
        Post laCroix = new Post("La Croix", "My favorite flavor of La Croix is Limoncello.");
        model.addAttribute("post", laCroix);
        return "/posts/show";
    }

    //GET the form for creating a post
    @GetMapping("/posts/create")
    public String createPostDoGet() {
        return "/posts/new";
    }

    //POST the created post
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPostDoPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        Post post = new Post(title, body);
        Post dbPost = postDao.save(post);
        return "create a new Post with the id: " + dbPost.getId();
    }

}
