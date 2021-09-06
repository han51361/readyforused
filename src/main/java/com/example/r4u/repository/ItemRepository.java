package com.example.r4u.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchService;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepository {

    private final RestHighLevelClient restHighLevelClient;

    public SearchResponse findAllItem() throws IOException{
        SearchRequest searchRequest =  new SearchRequest("3d500_thecheat_data");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        log.info("searchRequest info : {}",searchRequest.source(searchSourceBuilder));
        try{
            SearchResponse searchResponse  = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            return searchResponse;
        } catch (IOException e){
            System.out.println("Elastic search Fail");
            return null;
        }

    }
}
