package com.thereputeo.awesomeanonymousforum.api;

import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.Result;
import com.thereputeo.awesomeanonymousforum.model.ApiResponseWrapper;
import com.thereputeo.awesomeanonymousforum.service.PostOperationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostOperationsService postOperationsService;


    @Autowired
    public PostController(PostOperationsService postOperationsService) {
        this.postOperationsService = postOperationsService;
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
    public ResponseEntity getPostByAuthor(@PathVariable String authorName) {
        return new ResponseEntity<>(new ApiResponseWrapper<>(postOperationsService.getAllPostByAuthor(authorName)), HttpStatus.OK);
    }
}
