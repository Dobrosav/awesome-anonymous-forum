package com.thereputeo.awesomeanonymousforum;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.Result;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoaService;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoabInterface;
import com.thereputeo.awesomeanonymousforum.client.whoa.model.MovieDetail;
import com.thereputeo.awesomeanonymousforum.database.entity.Comment;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import com.thereputeo.awesomeanonymousforum.database.repository.CommentRepo;
import com.thereputeo.awesomeanonymousforum.database.repository.PostRepo;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import com.thereputeo.awesomeanonymousforum.service.CommentService;
import com.thereputeo.awesomeanonymousforum.service.PostOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AwesomeAnonymousForumApplicationTests {

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

    private PostOperationsService postOperationsService;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        whoaService = new WhoaService(whoabInterface);
        postOperationsService = new PostOperationsService(postRepo, whoaService);
        commentService = new CommentService(postRepo, commentRepo);
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
        List<Post> posts = List.of(new Post());
        when(postRepo.findByAuthorName(author)).thenReturn(posts);

        List<Post> result = postOperationsService.getAllPostByAuthor(author);

        assertEquals(posts, result);
        verify(postRepo).findByAuthorName(author);
    }

    @Test
    void getPostsByAuthor_NotFound() {
        String author = "testAuthor";
        when(postRepo.findByAuthorName(author)).thenReturn(List.of());

        assertThrows(ServiceException.class, () -> postOperationsService.getAllPostByAuthor(author));
        verify(postRepo).findByAuthorName(author);
    }


    @Test
    void createComment_PostNotFound() {
        CommentDto commentDto = new CommentDto();
        when(postRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> commentService.createCommentOnPost(1, commentDto));
        verify(postRepo).findById(1);
    }

}

