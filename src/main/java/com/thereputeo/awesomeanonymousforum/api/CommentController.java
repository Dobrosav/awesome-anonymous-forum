package com.thereputeo.awesomeanonymousforum.api;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse;
import com.thereputeo.awesomeanonymousforum.service.CommentService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{parentCommentId}/replies")
    public ResponseEntity<CommentResponse> createCommentOnComment(@PathVariable int parentCommentId, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createReplyOnComment(parentCommentId, commentDto), HttpStatus.CREATED);
    }
}

