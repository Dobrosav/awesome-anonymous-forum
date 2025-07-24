package com.thereputeo.awesomeanonymousforum.client.whoa;

import com.thereputeo.awesomeanonymousforum.client.whoa.model.WhoaClientResponse;
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

@Service
public class WhoaService {

    private static final Logger log = LoggerFactory.getLogger(WhoaService.class);

    private final WhoabInterface whoabInterface;


    @Autowired
    public WhoaService(WhoabInterface whoanterface) {
        this.whoabInterface = whoanterface;
    }

    public WhoaClientResponse getMovie() {
        Call<WhoaClientResponse> serviceCall = whoabInterface.getRandomWhoaMovie();
        Response<WhoaClientResponse> response = null;
        try {
            response = serviceCall.execute();

            if (!response.isSuccessful()) {

            }
        } catch (IOException e) {
            log.error("Error while invoking cpahub:{}", e.getMessage());
            throw new ServiceException(ErrorType.CLIENT_NOT_AVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Successfully got whoa movie:{}", response.body());
        return response.body();
    }

}

