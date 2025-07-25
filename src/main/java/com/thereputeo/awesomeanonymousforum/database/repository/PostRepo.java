package com.thereputeo.awesomeanonymousforum.database.repository;

import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post, Integer> {
    List<Post> findByAuthorName(String authorName);

    @Query("SELECT p FROM Post p")
    Page<Post> findAll(Pageable pageable);
}
