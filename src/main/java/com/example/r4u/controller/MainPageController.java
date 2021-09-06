package com.example.r4u.controller;


import com.example.r4u.domain.Item;
import com.example.r4u.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

   @GetMapping("/test")
    public String  test(Model model) throws IOException{
        List<Item> itemList = searchService.searchAll();
        model.addAttribute("test_List",itemList);
        return "realmain";
   }
}
