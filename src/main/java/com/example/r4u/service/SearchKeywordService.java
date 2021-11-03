package com.example.r4u.service;


import com.example.r4u.domain.SearchItemKeyword;
import com.example.r4u.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchKeywordService {

    @Autowired
    private SearchKeywordRepository searchKeywordRepository;

    private String totalHits;


    public List<SearchItemKeyword> searchItemKeywords(String input) throws IOException{

        SearchResponse searchResponse =  searchKeywordRepository.findKeyword(input);

        SearchHits hits = searchResponse.getHits();

        List<SearchItemKeyword> searchItemKeywordList = new ArrayList<>();

        int searchKeywordCount = (int)hits.getTotalHits().value;
        log.info("전체 키워드 수 : {}",searchKeywordCount);
        setTotalHits(String.valueOf(searchKeywordCount));
        Aggregations aggregations = searchResponse.getAggregations();
        Terms terms = aggregations.get("item_keyword");

       for(Terms.Bucket keyword: terms.getBuckets()){
           String key = (String) keyword.getKey();
           long keywordCount = keyword.getDocCount();

            searchItemKeywordList.add(new SearchItemKeyword(key,keywordCount));
       }
        return searchItemKeywordList;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    public String getTotalHits() {
        return totalHits;
    }
}
