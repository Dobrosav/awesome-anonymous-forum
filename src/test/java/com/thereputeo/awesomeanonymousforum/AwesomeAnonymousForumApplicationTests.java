package com.thereputeo.awesomeanonymousforum;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.PostResponse;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoaService;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoabInterface;
import com.thereputeo.awesomeanonymousforum.client.whoa.model.MovieDetail;
import com.thereputeo.awesomeanonymousforum.database.entity.Comment;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import com.thereputeo.awesomeanonymousforum.database.repository.CommentRepo;
import com.thereputeo.awesomeanonymousforum.database.repository.PostRepo;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import com.thereputeo.awesomeanonymousforum.mapper.PostMapper;
import com.thereputeo.awesomeanonymousforum.model.PostType;
import com.thereputeo.awesomeanonymousforum.service.CommentService;
import com.thereputeo.awesomeanonymousforum.service.PostOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AwesomeAnonymousForumApplicationTests extends AbstractIntegrationTest {

    @Mock
    private WhoabInterface whoabInterface;

    @Mock
    private Call<List<MovieDetail>> call;

    @Mock
    private PostRepo postRepo;

    @Mock
    private WhoaService whoaService;

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private PostMapper postMapper;

    @Mock
    private com.thereputeo.awesomeanonymousforum.mapper.CommentMapper commentMapper;

    private PostOperationsService postOperationsService;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        whoaService = new WhoaService(whoabInterface);
        postOperationsService = new PostOperationsService(postRepo, whoaService, postMapper);
        commentService = new CommentService(postRepo, commentRepo, commentMapper);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getMovie_Success() throws IOException {
        List<MovieDetail> expectedMovies = List.of(new MovieDetail());
        when(whoabInterface.getRandomWhoaMovie()).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(expectedMovies));

        List<MovieDetail> result = whoaService.getMovie();

        assertEquals(expectedMovies, result);
        verify(whoabInterface).getRandomWhoaMovie();
        verify(call).execute();
    }

    @Test
    void getMovie_IoException() throws IOException {
        when(whoabInterface.getRandomWhoaMovie()).thenReturn(call);
        when(call.execute()).thenThrow(IOException.class);

        assertThrows(ServiceException.class, () -> whoaService.getMovie());
        verify(whoabInterface).getRandomWhoaMovie();
        verify(call).execute();
    }

    @Test
    void getPostsByAuthor_Success() {
        String author = "testAuthor";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Post post = new Post();
        List<Post> postsList = List.of(post);
        Page<Post> posts = new PageImpl<>(postsList, pageable, postsList.size());
        PostResponse postResponse = new PostResponse();
        
        when(postRepo.findByAuthorName(author, pageable)).thenReturn(posts);
        when(postMapper.toResponse(post)).thenReturn(postResponse);

        Page<PostResponse> result = postOperationsService.getAllPostByAuthor(author, page, size);

        assertEquals(1, result.getTotalElements());
        assertEquals(postResponse, result.getContent().get(0));
        verify(postRepo).findByAuthorName(author, pageable);
        verify(postMapper).toResponse(post);
    }

    @Test
    void shouldCreatePostSuccessfullyWithoutIncludeKeanuWhoa() {
        PostDto postDto = new PostDto();
        postDto.setContent("Amazing content");
        postDto.setAuthorName("Author1");
        postDto.setExternalLink("http://example.com");
        postDto.setPostType(PostType.PLAIN_TEXT);
        postDto.setIncludeKeanuWhoa(false);

        Post mockPost = new Post();
        PostResponse mockResponse = new PostResponse();
        
        when(postMapper.toEntity(postDto)).thenReturn(mockPost);
        when(postRepo.save(mockPost)).thenReturn(mockPost);
        when(postMapper.toResponse(mockPost)).thenReturn(mockResponse);

        PostResponse result = postOperationsService.createPost(postDto);

        assertNotNull(result);
        verify(postMapper).toEntity(postDto);
        verify(postRepo).save(mockPost);
        verify(postMapper).toResponse(mockPost);
    }

    @Test
    void getPostsByAuthor_NotFound() {
        String author = "testAuthor";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        when(postRepo.findByAuthorName(author, pageable)).thenReturn(Page.empty());

        assertThrows(ServiceException.class, () -> postOperationsService.getAllPostByAuthor(author, page, size));
        verify(postRepo).findByAuthorName(author, pageable);
    }

    @Test
    void testGetAllPost_EmptyResult() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        when(postRepo.findAll(pageable)).thenReturn(Page.empty());

        Page<PostResponse> result = postOperationsService.getAllPost(page, size);

        assertTrue(result.isEmpty(), "Result should have been empty.");
        verify(postRepo, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllPost_WithResults() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Post post = new Post("Sample Post", "General", null, null, null, null, "Author");
        List<Post> postList = Collections.singletonList(post);
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());
        PostResponse postResponse = new PostResponse();

        when(postRepo.findAll(pageable)).thenReturn(postPage);
        when(postMapper.toResponse(post)).thenReturn(postResponse);

        Page<PostResponse> result = postOperationsService.getAllPost(page, size);

        assertEquals(1, result.getTotalElements(), "Result should contain 1 element.");
        assertEquals(postResponse, result.getContent().get(0), "Retrieved post should match the expected post.");
        verify(postRepo, times(1)).findAll(pageable);
        verify(postMapper).toResponse(post);
    }

    @Test
    void createComment_PostNotFound() {
        CommentDto commentDto = new CommentDto();
        when(postRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> commentService.createCommentOnPost(1, commentDto));
        verify(postRepo).findById(1);
    }

    @Test
    void createReplyOnComment_ShouldCreateReplySuccessfully() {
        Integer parentCommentId = 1;
        Comment parentComment = new Comment();
        parentComment.setId(parentCommentId);
        parentComment.setPost(new Post());
        when(commentRepo.findById(parentCommentId)).thenReturn(Optional.of(parentComment));
        
        Comment savedComment = new Comment();
        when(commentRepo.save(any(Comment.class))).thenReturn(savedComment);

        CommentDto replyDto = new CommentDto();
        replyDto.setContent("This is a reply");
        replyDto.setAuthorName("Author");
        
        Comment mappedComment = new Comment();
        when(commentMapper.toEntity(replyDto)).thenReturn(mappedComment);
        
        com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse mockResponse = new com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse();
        when(commentMapper.toResponse(savedComment)).thenReturn(mockResponse);

        com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse result = commentService.createReplyOnComment(parentCommentId, replyDto);

        assertNotNull(result);
        verify(commentRepo, times(1)).save(any(Comment.class));
    }
}
