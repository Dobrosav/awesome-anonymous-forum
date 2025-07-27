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
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        HostnameVerifier hostnameVerifier = (hostname, session) -> true;
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier(hostnameVerifier)
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
