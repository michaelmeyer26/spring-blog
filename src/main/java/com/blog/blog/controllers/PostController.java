package com.blog.blog.controllers;

import com.blog.blog.models.Post;
import com.blog.blog.models.User;
import com.blog.blog.repos.PostRepository;
import com.blog.blog.repos.UserRepository;
import com.blog.blog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final UserRepository userDao;
    private final PostRepository postDao;
    private final EmailService emailService;

    public PostController(UserRepository userDao, PostRepository postDao, EmailService emailService) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String postsIndex(Model viewModel) {
        viewModel.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postId(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }

    //GET edit form
    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "posts/edit";
    }

    //POST edit values
    @PostMapping("/posts/{id}/edit")
    public String editPost(@ModelAttribute Post postToBeUpdated) {
        User user = userDao.getOne(1L);
        postToBeUpdated.setOwner(user);
        postDao.save(postToBeUpdated);
        return "redirect:/posts/" + postToBeUpdated.getId();
    }

    //GET the form for creating a post
    @GetMapping("/posts/create")
    public String createPostDoGet(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    //POST the created post
    @PostMapping("/posts/create")
    public String createPostDoPost(@ModelAttribute Post post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //just get the first user in the db
        post.setOwner(user);
        Post dbPost = postDao.save(post);
        emailService.prepareAndSend(dbPost, "Post successfully created", "You can find it with the id of " + dbPost.getId());
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
        return "posts/index";
    }
}
