package com.example.r4u.config;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;


@Configuration
public class ElasticConfig {
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

//    @Value("${elasticsearch.username}")
//    private String username;
//
//    @Value("${elasticsearch.password}")
//    private String password;


    //클라우드 연동 용
//    @Bean
//    public RestHighLevelClient restHighLevelClient(){
//       final CredentialsProvider credentialsProvider =
//               new BasicCredentialsProvider();
//       credentialsProvider.setCredentials(AuthScope.ANY,
//               new UsernamePasswordCredentials(username,password));
//
//    //    String encodedBytes = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
//
//        RestClientBuilder builder = RestClient.builder(new HttpHost(host,port,"https"))
//                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                    }
//                });


//        Header[] headers = {
//                new BasicHeader(HttpHeaders.CONTENT_TYPE , "application/json"),
//                new BasicHeader("Authorization", "Basic"+encodedBytes )};

       // builder.setDefaultHeaders(headers);

//        return new RestHighLevelClient(builder);
//
//
//    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host,port,"http")));
    }



}
