package com.example.r4u.domain;


import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
public class SearchItemKeyword {

    private String itemKeyword;

    private String keywordCount;

    DecimalFormat formatter = new DecimalFormat("###,###");

    public SearchItemKeyword(String itemKeyword, long keywordCount) {
        this.itemKeyword = itemKeyword;
        this.keywordCount = formatter.format(keywordCount);
    }
}
