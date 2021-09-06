package com.example.r4u.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TotalScamInfo {
    /*어차피 실시간으로 늘어나는 거 보여주려면
     프론트 단에서 그냥 ajax 써서 직접 ES 접근하는게 나을지도
     일단 임시용
     */
    private int totalScamCount;

    private int totalScamPrice;

    private Date date;
}
