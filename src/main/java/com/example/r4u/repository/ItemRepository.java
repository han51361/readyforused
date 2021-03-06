package com.example.r4u.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchService;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.StringTokenizer;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepository {

    private final RestHighLevelClient restHighLevelClient;

    public SearchResponse findAllItem() throws IOException{
        SearchRequest searchRequest =  new SearchRequest("3d500_thecheat_data");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum_price").field("price");
        ValueCountAggregationBuilder valueCountAggregationBuilder = AggregationBuilders.count("all_fraud").field("id.keyword");
        searchSourceBuilder.aggregation(sumAggregationBuilder);
        searchSourceBuilder.aggregation(valueCountAggregationBuilder);
        searchSourceBuilder.sort("datetime",SortOrder.DESC);
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

    //?????? ?????? ???????????? ??????
    // bool - must&match / should&match_phrase
    public SearchResponse findTextItemDeal(String input, int page) throws IOException {

        //???????????? ?????? ???????????? ?????? ??????
        StringTokenizer st = new StringTokenizer(input," ");
        String no_space_input = input.replaceAll(" ","");

        log.info("no_space_input {}",no_space_input);

        SearchRequest searchRequest =  new SearchRequest("3d500_thecheat_data");
        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("goods",input))
                .should(QueryBuilders.matchPhraseQuery("goods",no_space_input)));

        searchSourceBuilder.from(10*(page-1)).size(10).sort("datetime", SortOrder.DESC);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        log.info("?????? ?????? {}",searchResponse);
        return searchResponse;

    }
    //?????? ?????? ?????? ??????
    public SearchResponse getItemFraudTrendCard(String input) throws IOException {
        //???????????? ?????? ???????????? ?????? ??????
        StringTokenizer st = new StringTokenizer(input," ");
        String no_space_input = input.replaceAll(" ","");

        SearchRequest searchRequest =  new SearchRequest("3d500_thecheat_data");
        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("goods",input))
                .should(QueryBuilders.matchPhraseQuery("goods",no_space_input)));
        searchSourceBuilder.size(0);
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("avg_price").field("price");
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum_price").field("price");

        //todo ?????? ????????? ???????????? ???????????? ?????? ?????? ?????? ?????? -> ?????? 1-8??? ???????????? ????????????

        DateRangeAggregationBuilder recentDateRangeAggregationBuilder = AggregationBuilders.dateRange("recent_fraud").field("datetime").addRange("now-120d/d","now");
        DateRangeAggregationBuilder compareDateRangeAggregationBuilder =  AggregationBuilders.dateRange("compare_fraud").field("datetime").addRange("now-240d/d","now-120d/d");

        searchSourceBuilder.aggregation(avgAggregationBuilder);
        searchSourceBuilder.aggregation(sumAggregationBuilder);
        searchSourceBuilder.aggregation(recentDateRangeAggregationBuilder);
        searchSourceBuilder.aggregation(compareDateRangeAggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        log.info("?????? ?????? {}",searchResponse);

        return searchResponse;

    }

}
