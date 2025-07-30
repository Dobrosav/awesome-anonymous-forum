package com.thereputeo.awesomeanonymousforum.service;

import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.Result;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoaService;
import com.thereputeo.awesomeanonymousforum.client.whoa.model.MovieDetail;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import com.thereputeo.awesomeanonymousforum.database.repository.PostRepo;
import com.thereputeo.awesomeanonymousforum.exception.ErrorType;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    public PostOperationsService(PostRepo postRepo, WhoaService whoaService) {
        this.postRepo = postRepo;
        this.whoaService = whoaService;
    }

    public Result createPost(PostDto postDto) {
        logger.info("Creating post with details: {}", postDto.toString().replace("\n", ""));
        Result result = new Result();
        Post post = new Post();
        post.setLink(postDto.getExternalLink());
        post.setAuthorName(postDto.getAuthorName());
        post.setText(postDto.getContent());
        post.setType(postDto.getPostType().name());
        if (postDto.getIncludeKeanuWhoa()) {
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
        postRepo.save(post);
        result.setSuccess(true);
        result.setMessage("Successfully saved post");
        logger.info("Successfully saved post with details: {}", postDto.toString().replace("\n", ""));
        return result;
    }

    public Page<Post> getAllPost(int page, int size) {
        logger.info("Getting all posts on page:{} and size:{}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return postRepo.findAll(pageable);
    }

    public List<Post> getAllPostByAuthor(String authorName) {
        logger.info("Getting all posts by author:{}", authorName);
        List<Post> result = postRepo.findByAuthorName(authorName);
        if (result.isEmpty()) {
            logger.warn("No post found for author:{}", authorName);
            throw new ServiceException(ErrorType.NOT_FOUND_POST, HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
