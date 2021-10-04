package com.example.r4u.controller;


import com.example.r4u.domain.Item;
import com.example.r4u.domain.ItemFraudTrendCard;
import com.example.r4u.domain.TotalScamInfo;
import com.example.r4u.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class MainPageController {

    private final SearchService searchService;

    @Autowired
    public MainPageController(SearchService searchService){
        this.searchService = searchService;
    }

   @GetMapping("/main")
    public String  test(Model model) throws IOException{
        List<Item> itemList = searchService.searchAll();
        TotalScamInfo allFraud = searchService.getTotalScamInfo();
        model.addAttribute("all_fraud", allFraud);
        model.addAttribute("test_List",itemList);
        return "realmain";
   }

   @GetMapping("/search")
    public String search(String input, @RequestParam(value = "page",defaultValue = "1") Integer page, Model model) throws  IOException{

        // 시간 측정
       StopWatch stopWatch = new StopWatch();
       stopWatch.start();

       ItemFraudTrendCard trendCard = searchService.getFraudCard(input);

       if(page < 1){
           page = 1 ;
       }
       SearchHits result_exist = searchService.getSearchHits(input, page);
       System.out.println(result_exist.getTotalHits().value);
       if(result_exist.getTotalHits().value == 0){
           return "/no_result";
       }

       List<Item> tableTransInfoList = searchService.searchItemDealInfo(input, page);
       stopWatch.stop();
       String searchTime = String.valueOf(stopWatch.getLastTaskTimeMillis());
       String totalCheatCount =  searchService.getTotalHits();

       model.addAttribute("trend_card",trendCard);
       model.addAttribute("input_string", input);
       model.addAttribute("table_transInfo",tableTransInfoList);
       model.addAttribute("page_num",page);
       model.addAttribute("total_cheat_count",totalCheatCount);
       model.addAttribute("searchtime",searchTime);

       return "/searchItem";



   }
}
