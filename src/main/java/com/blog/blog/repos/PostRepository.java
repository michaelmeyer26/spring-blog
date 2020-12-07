package com.blog.blog.repos;

import com.blog.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //Query Methods
//    Post findByDescription(String desc);
    List<Post> findAllByTitleIsLike(String term);
}
