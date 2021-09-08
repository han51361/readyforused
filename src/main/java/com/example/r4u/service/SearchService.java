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
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    @Autowired
    private ItemRepository itemRepository;

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

    public List<Item> searchItemDealInfo(String input,int page) throws IOException {
        SearchResponse searchResponse = itemRepository.findTextItemDeal(input, page);
        SearchHits hits = searchResponse.getHits();


    }


}
