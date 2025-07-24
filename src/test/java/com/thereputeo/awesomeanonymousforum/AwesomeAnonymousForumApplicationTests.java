package com.thereputeo.awesomeanonymousforum;

import com.thereputeo.awesomeanonymousforum.client.whoa.WhoaService;
import com.thereputeo.awesomeanonymousforum.client.whoa.WhoabInterface;
import com.thereputeo.awesomeanonymousforum.client.whoa.model.WhoaClientResponse;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AwesomeAnonymousForumApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private WhoabInterface whoabInterface;

	@Mock
	private Call<WhoaClientResponse> call;

	private WhoaService whoaService;

	@BeforeEach
	void setUp() {
		whoaService = new WhoaService(whoabInterface);
	}

	@Test
	void getMovie_Success() throws IOException {
		// Arrange
		WhoaClientResponse expectedResponse = new WhoaClientResponse();
		Response<WhoaClientResponse> retrofitResponse = Response.success(expectedResponse);

		when(whoabInterface.getRandomWhoaMovie()).thenReturn(call);
		when(call.execute()).thenReturn(retrofitResponse);

		// Act
		WhoaClientResponse result = whoaService.getMovie();

		// Assert
		assertNotNull(result);
		assertEquals(expectedResponse, result);
		verify(whoabInterface).getRandomWhoaMovie();
		verify(call).execute();
	}

}

