package com.example.r4u.service;

import com.example.r4u.domain.Item;
import com.example.r4u.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    @Autowired
    private ItemRepository itemRepository;

    String search_hits;

    public List<Item> searchAll() throws IOException {
        SearchResponse searchResponse = itemRepository.findAllItem();

        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();

        int numHits =(int) totalHits.value;
        log.info("total hits : {}",numHits);

        List<Item> itemList = new ArrayList<>();

        hits.forEach(hit -> {
            Map<String,Object> result =  hit.getSourceAsMap();
            itemList.add(Item.builder()
                    .name(String.valueOf(result.get("goods")))
                    .id(Long.valueOf((String) result.get("id")))
                    .price(Integer.parseInt(String.valueOf(result.get("price"))))
                    .platform(String.valueOf(result.get("platform")))
                    .outline(String.valueOf(result.get("outline")))
                    .timestamp(String.valueOf(result.get("datetime")))
                    .category(String.valueOf(result.get("category")))
                    .build());
            System.out.println("result = " + result);
        });

            return itemList;
    }

    // 사기거래 정보 in 테이블
    public List<Item> searchItemDealInfo(String input,int page) throws IOException {
        SearchResponse searchResponse = itemRepository.findTextItemDeal(input, page);
        SearchHits hits = searchResponse.getHits();

        search_hits = String.valueOf(hits.getTotalHits().value);
        log.info("totalHits",search_hits);

        List<Item> itemDealList = new ArrayList<>();

        if(!hits.equals(null)){
            hits.forEach(hit -> {
                Map<String, Object> transInfo =  hit.getSourceAsMap();
                itemDealList.add(Item.builder()
                        .name(String.valueOf(transInfo.get("goods")))
                        .id(Long.valueOf((String) transInfo.get("id")))
                        .price(Integer.parseInt(String.valueOf(transInfo.get("price"))))
                        .platform(String.valueOf(transInfo.get("platform")))
                        .outline(String.valueOf(transInfo.get("outline")))
                        .timestamp(String.valueOf(transInfo.get("datetime")))
                        .category(String.valueOf(transInfo.get("category")))
                        .build());
                log.info("result = {}",transInfo);
            });
        }
        return itemDealList;

    }

    //해당 품목 사기 거래 건수 반환
    public String getTotalHits(){
        return search_hits;
    }



}
