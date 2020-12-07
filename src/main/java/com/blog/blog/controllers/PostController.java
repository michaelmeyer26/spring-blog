package com.blog.blog.controllers;

import com.blog.blog.models.Post;
import com.blog.blog.models.User;
import com.blog.blog.repos.PostRepository;
import com.blog.blog.repos.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final UserRepository userDao;
    private final PostRepository postDao;

    public PostController(UserRepository userDao, PostRepository postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String postsIndex(Model viewModel) {
        viewModel.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postId(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "/posts/show";
    }

    //GET edit form
    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "/posts/edit";
    }

    //POST edit values
    @PostMapping("/posts/{id}/edit")
    public String editPost(
            @PathVariable long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        Post dbPost = postDao.getOne(id);
        dbPost.setTitle(title);
        dbPost.setBody(body);
        postDao.save(dbPost);
        return "redirect:/posts/" + dbPost.getId();
    }

    //GET the form for creating a post
    @GetMapping("/posts/create")
    public String createPostDoGet() {
        return "/posts/new";
    }

    //POST the created post
    @PostMapping("/posts/create")
    public String createPostDoPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body
    ) {
        User user = userDao.getOne(1L); //just get the first user in the db
        Post post = new Post(title, body);
        post.setOwner(user);
        Post dbPost = postDao.save(post);
        return "redirect:/posts/" + dbPost.getId();
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/posts/search")
    public String search(@RequestParam(name = "term") String term, Model viewModel) {
        term = "%" + term + "%";
        List<Post> dbPost = postDao.findAllByTitleIsLike(term);
        viewModel.addAttribute("posts", dbPost);
        return "/posts/index";
    }
}
