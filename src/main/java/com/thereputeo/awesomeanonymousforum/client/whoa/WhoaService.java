package com.thereputeo.awesomeanonymousforum.client.whoa;

import com.thereputeo.awesomeanonymousforum.client.whoa.model.MovieDetail;
import com.thereputeo.awesomeanonymousforum.exception.ErrorType;
import com.thereputeo.awesomeanonymousforum.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class WhoaService {

    private static final Logger log = LoggerFactory.getLogger(WhoaService.class);

    private final WhoabInterface whoabInterface;


    @Autowired
    public WhoaService(WhoabInterface whoanterface) {
        this.whoabInterface = whoanterface;
    }

    public List<MovieDetail> getMovie() {
        Call<List<MovieDetail>> serviceCall = whoabInterface.getRandomWhoaMovie();
        Response<List<MovieDetail>> response = null;
        try {
            response = serviceCall.execute();

            if (!response.isSuccessful()) {
                log.error("Error while invoking whoa service:{}", response.errorBody());
            }
        } catch (IOException e) {
            log.error("Error while invoking whoa service:{}", e.getMessage());
            throw new ServiceException(ErrorType.CLIENT_NOT_AVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Successfully got whoa movie:{}", response.body());
        return response.body();
    }

}

