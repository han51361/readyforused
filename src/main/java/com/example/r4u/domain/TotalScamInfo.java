package com.example.r4u.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.DecimalFormat;

@Getter
@Setter
public class TotalScamInfo {
    /*어차피 실시간으로 늘어나는 거 보여주려면
     프론트 단에서 그냥 ajax 써서 직접 ES 접근하는게 나을지도
     일단 임시용
     */
    private String totalScamCount;

    private String totalScamPrice;

    private Date date;

    DecimalFormat formatter = new DecimalFormat("###,###");

    public TotalScamInfo(int totalScamCount,int totalScamPrice) {
        System.out.println(totalScamCount);
        System.out.println(totalScamPrice);
        this.totalScamCount = formatter.format(totalScamCount);
        this.totalScamPrice = formatter.format(totalScamPrice);
    }
}
