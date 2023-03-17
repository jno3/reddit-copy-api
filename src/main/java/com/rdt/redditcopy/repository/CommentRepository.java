package com.rdt.redditcopy.repository;

import com.rdt.redditcopy.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
