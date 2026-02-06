package com.thereputeo.awesomeanonymousforum.client.whoa;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Configuration
public class WoahClientConfig {

    @Value("${whoa.client.baseUrl}")
    private String apiBaseUrl;

    @Bean
    public WhoabInterface getWhoaInterface(@Qualifier("getWhoaHttpClient") OkHttpClient okHttpClient, @Qualifier("getObjectMapper") ObjectMapper objectMapper) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory((JacksonConverterFactory.create(objectMapper)))
                .client(okHttpClient)
                .build();
        return retrofit.create(WhoabInterface.class);
    }

    @Bean
    public OkHttpClient getWhoaHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        return new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(10, 5L, TimeUnit.MINUTES))
                .build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setTimeZone(TimeZone.getTimeZone("CET"));
        return mapper;
    }

}
