package com.woon7713.simple_board.repository;

import com.woon7713.simple_board.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
