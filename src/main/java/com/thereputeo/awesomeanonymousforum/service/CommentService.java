package com.thereputeo.awesomeanonymousforum.service;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
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


@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final com.thereputeo.awesomeanonymousforum.mapper.CommentMapper commentMapper;

    public CommentService(PostRepo postRepo, CommentRepo commentRepo, com.thereputeo.awesomeanonymousforum.mapper.CommentMapper commentMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.commentMapper = commentMapper;
    }

    public com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse createCommentOnPost(Integer postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) {
            logger.warn("No post found for id:{}", postId);
            throw new ServiceException(ErrorType.NOT_FOUND_POST, HttpStatus.NOT_FOUND);
        }
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setPost(post);
        comment.setParentComment(null);

        Comment savedComment = commentRepo.save(comment);
        logger.info("Successfully created comment on post {} with details: {}", postId, commentDto.toString().replace("\n", ""));
        return commentMapper.toResponse(savedComment);
    }

    public com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse createReplyOnComment(Integer commentId, CommentDto commentDto) {
        Comment parentcomment = commentRepo.findById(commentId).orElse(null);
        if (parentcomment == null) {
            logger.warn("No parent comment found for id:{}", commentId);
            throw new ServiceException(ErrorType.NOT_FOUND_COMMENT, HttpStatus.NOT_FOUND);
        }
        Post post = parentcomment.getPost();
        Comment newComment = commentMapper.toEntity(commentDto);
        newComment.setParentComment(parentcomment);
        newComment.setPost(post);

        Comment savedComment = commentRepo.save(newComment);
        logger.info("Successfully created new reply on comment {} with details: {}", commentId, commentDto.toString().replace("\n", ""));
        return commentMapper.toResponse(savedComment);

    }

}
