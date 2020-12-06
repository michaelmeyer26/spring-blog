package com.blog.blog.controllers;

import com.blog.blog.models.Post;
import com.blog.blog.repos.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        List posts = new ArrayList<>();
        Post pilot = new Post("pilot", "This is the first post of my blog!");
        posts.add(pilot);
        Post laCroix = new Post("La Croix", "My favorite flavor of La Croix is Limoncello.");
        posts.add(laCroix);
        model.addAttribute("posts", posts);
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
    @ResponseBody
    public String createPostDoGet() {
        return "View the form for creating a post";
    }

    //POST the created post
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPostDoPost() {
        return "Create a new post";
    }


}
