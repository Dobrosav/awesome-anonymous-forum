package com.thereputeo.awesomeanonymousforum.service;

import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.PostResponse;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoaService;
import com.thereputeo.awesomeanonymousforum.client.whoa.model.MovieDetail;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import com.thereputeo.awesomeanonymousforum.database.repository.PostRepo;
import com.thereputeo.awesomeanonymousforum.exception.ErrorType;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import com.thereputeo.awesomeanonymousforum.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostOperationsService {

    private static final Logger logger = LoggerFactory.getLogger(PostOperationsService.class);

    private final PostRepo postRepo;
    private final WhoaService whoaService;
    private final PostMapper postMapper;

    @Autowired
    public PostOperationsService(PostRepo postRepo, WhoaService whoaService, PostMapper postMapper) {
        this.postRepo = postRepo;
        this.whoaService = whoaService;
        this.postMapper = postMapper;
    }

    public PostResponse createPost(PostDto postDto) {
        logger.info("Creating post with details: {}", postDto.toString().replace("\n", ""));

        Post post = postMapper.toEntity(postDto);

        if (Boolean.TRUE.equals(postDto.getIncludeKeanuWhoa())) {
            List<MovieDetail> whoaClientResponse = whoaService.getMovie();
            if (whoaClientResponse == null || whoaClientResponse.isEmpty()) {
                logger.warn("whoa client is not available");
                throw new ServiceException(ErrorType.CLIENT_NOT_AVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            MovieDetail movieDetail = whoaClientResponse.getFirst();
            post.setAudio(movieDetail.getAudio());
            post.setPoster(movieDetail.getPoster());
            post.setVideo(movieDetail.getVideo().get_1080p());
        }

        Post savedPost = postRepo.save(post);
        logger.info("Successfully saved post with id: {}", savedPost.getId());
        return postMapper.toResponse(savedPost);
    }

    public Page<PostResponse> getAllPost(int page, int size) {
        logger.info("Getting all posts on page:{} and size:{}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepo.findAll(pageable);
        List<PostResponse> responses = posts.stream().map(postMapper::toResponse).toList();
        return new PageImpl<>(responses, pageable, posts.getTotalElements());
    }

    public Page<PostResponse> getAllPostByAuthor(String authorName, int page, int size) {
        logger.info("Getting all posts by author:{} on page:{} and size:{}", authorName, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> result = postRepo.findByAuthorName(authorName, pageable);
        if (result.isEmpty()) {
            logger.warn("No post found for author:{}", authorName);
            throw new ServiceException(ErrorType.NOT_FOUND_POST, HttpStatus.NOT_FOUND);
        }
        List<PostResponse> responses = result.stream().map(postMapper::toResponse).toList();
        return new PageImpl<>(responses, pageable, result.getTotalElements());
    }
}

