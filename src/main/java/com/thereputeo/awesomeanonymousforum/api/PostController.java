package com.thereputeo.awesomeanonymousforum.api;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse;
import com.thereputeo.awesomeanonymousforum.api.model.response.PostResponse;
import com.thereputeo.awesomeanonymousforum.service.CommentService;
import com.thereputeo.awesomeanonymousforum.service.PostOperationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(postOperationsService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPost(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(postOperationsService.getAllPost(page, size), HttpStatus.OK);
    }

    @GetMapping("by-author/{authorName}")
    public ResponseEntity<Page<PostResponse>> getPostByAuthor(@PathVariable String authorName, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(postOperationsService.getAllPostByAuthor(authorName, page, size), HttpStatus.OK);
    }

    @PostMapping(value = "/{postId}/comments")
    public ResponseEntity<CommentResponse> createCommentOnPost(@PathVariable int postId, @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createCommentOnPost(postId, commentDto), HttpStatus.CREATED);
    }
}

