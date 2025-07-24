package com.thereputeo.awesomeanonymousforum.api;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.Result;
import com.thereputeo.awesomeanonymousforum.model.ApiResponseWrapper;
import com.thereputeo.awesomeanonymousforum.service.CommentService;
import com.thereputeo.awesomeanonymousforum.service.PostOperationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostOperationsService postOperationsService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostOperationsService postOperationsService, CommentService commentService) {
        this.postOperationsService = postOperationsService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Result>> createPost(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(new ApiResponseWrapper<>(postOperationsService.createPost(postDto)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<Iterator>> getAllPost(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(new ApiResponseWrapper<>(postOperationsService.getAllPost()), HttpStatus.OK);
    }

    @GetMapping("by-author/{authorName}")
    public ResponseEntity<ApiResponseWrapper<List>> getPostByAuthor(@PathVariable String authorName) {
        return new ResponseEntity<>(new ApiResponseWrapper<>(postOperationsService.getAllPostByAuthor(authorName)), HttpStatus.OK);
    }
    @PostMapping(value = "/{postId}/comments")
    public ResponseEntity<ApiResponseWrapper<Result>> createCommentOnPost(@PathVariable int postId,@RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(new ApiResponseWrapper<>(commentService.createCommentOnPost(postId, commentDto)), HttpStatus.CREATED);
    }
}
