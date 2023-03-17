package com.rdt.redditcopy.repository;

import com.rdt.redditcopy.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
