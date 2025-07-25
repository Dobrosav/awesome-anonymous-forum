package com.thereputeo.awesomeanonymousforum.database.repository;

import com.thereputeo.awesomeanonymousforum.database.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Integer> {

}
