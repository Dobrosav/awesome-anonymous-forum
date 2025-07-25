package com.thereputeo.awesomeanonymousforum.service;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.Result;
import com.thereputeo.awesomeanonymousforum.database.entity.Comment;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import com.thereputeo.awesomeanonymousforum.database.repository.CommentRepo;
import com.thereputeo.awesomeanonymousforum.database.repository.PostRepo;
import com.thereputeo.awesomeanonymousforum.exception.ErrorType;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;

    public CommentService(PostRepo postRepo, CommentRepo commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    public Result createCommentOnPost(Integer postId, CommentDto commentDto) {
        Result result = new Result();
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) {
            logger.warn("No post found for id:{}", postId);
            throw new ServiceException(ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setParentComment(null);
        comment.setPost(post);
        comment.setContent(commentDto.getContent());
        comment.setImageUrl(commentDto.getImageUrl());
        comment.setAuthorName(commentDto.getAuthorName());
        comment.setCreatedAt(new Date());
        result.setSuccess(true);
        result.setMessage("Successfully created comment on post");
        if (commentRepo.save(comment) == null) {
            result.setSuccess(false);
            result.setMessage("Failed to create comment on post");
            logger.warn("Failed to create comment on post {} with details: {}", postId, commentDto.toString().replace("\n", ""));
        } else {
            result.setSuccess(true);
            result.setMessage("Successfully created comment on post");
            logger.info("Successfully created comment on post {} with details: {}", postId, commentDto.toString().replace("\n", ""));
        }
        return result;
    }

    public Result createReplyOnComment(Integer commentId, CommentDto commentDto) {
        Result result = new Result();
        Comment parentcomment = commentRepo.findById(commentId).orElse(null);
        if (parentcomment == null) {
            logger.warn("No parentcomment found for id:{}", commentId);
            throw new ServiceException(ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Post post = parentcomment.getPost();
        Comment newComment = new Comment();
        newComment.setParentComment(parentcomment);
        newComment.setPost(post);
        newComment.setContent(commentDto.getContent());
        newComment.setImageUrl(commentDto.getImageUrl());
        newComment.setAuthorName(commentDto.getAuthorName());
        newComment.setCreatedAt(new Date());
        ;
        if (commentRepo.save(newComment) == null) {
            result.setSuccess(false);
            result.setMessage("Failed to create new reply on comment");
            logger.warn("Failed to create new reply on comment {}  with details: {}", commentId, commentDto.toString().replace("\n", ""));
        } else {
            result.setSuccess(true);
            result.setMessage("Successfully created new reply on comment");
            logger.info("Successfully created new reply on comment {} with details: {}", commentId, commentDto.toString().replace("\n", ""));
        }
        return result;

    }
}
