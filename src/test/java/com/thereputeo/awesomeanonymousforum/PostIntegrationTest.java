package com.thereputeo.awesomeanonymousforum;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import com.thereputeo.awesomeanonymousforum.database.repository.PostRepo;
import com.thereputeo.awesomeanonymousforum.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentService commentService;

    @Test
    void testSaveAndRetrievePost() {
        Post post = new Post();
        post.setText("Integration Test Content");
        post.setAuthorName("IntegrationTester");
        post.setType("PLAIN_TEXT");
        
        Post savedPost = postRepo.save(post);
        
        assertNotNull(savedPost.getId());
        
        Post retrievedPost = postRepo.findById(savedPost.getId()).orElseThrow();
        assertEquals("Integration Test Content", retrievedPost.getText());
        assertEquals("IntegrationTester", retrievedPost.getAuthorName());
    }

    @Test
    void testCreateCommentReturnsDto() {
        Post post = new Post();
        post.setText("Post for Comment");
        post.setAuthorName("Tester");
        post.setType("PLAIN_TEXT");
        Post savedPost = postRepo.save(post);

        CommentDto commentDto = new CommentDto();
        commentDto.setContent("This is a comment");
        commentDto.setAuthorName("Commenter");
        commentDto.setImageUrl("http://example.com/img.jpg");

        CommentResponse response = commentService.createCommentOnPost(savedPost.getId(), commentDto);

        assertNotNull(response);
        assertEquals("This is a comment", response.getContent());
        assertEquals("Commenter", response.getAuthorName());
        assertEquals(savedPost.getId(), response.getPostId());
    }
}
