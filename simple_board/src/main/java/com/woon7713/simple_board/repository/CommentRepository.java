package com.woon7713.simple_board.repository;

import com.woon7713.simple_board.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
