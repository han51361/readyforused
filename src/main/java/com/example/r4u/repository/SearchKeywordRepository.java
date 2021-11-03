package com.example.r4u.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SearchKeywordRepository {

    private final RestHighLevelClient restHighLevelClient;

    public SearchResponse findKeyword(String input) throws IOException{
        SearchRequest searchRequest = new SearchRequest("3d500_thecheat_data");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
        .must(QueryBuilders.wildcardQuery("goods","*"+input+"*")));
        searchSourceBuilder.size(0);

        TermsAggregationBuilder termsAggregationBuilder= AggregationBuilders.terms("item_keyword").field("goods.keyword");
        termsAggregationBuilder.size(20);

        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.info("물품 키워드 반환 {}",searchResponse.getAggregations());
        return searchResponse;
    }
}
