package com.example.r4u.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;


@Getter
@Setter
public class ItemFraudTrendCard {

    public String  totalCountOfFraud;

    public String totalAmountOfFraud;

    public String avgPriceOfFraud;

    public String registeredBoard;

    //최근 2개월 사기 피해 수
    public String lastTwoMonthFraud;
    // 대조를 위한 이전 6개월-4개월까지의 사기 피해 수
    public String contrastFraud;

    public String percentOfRecentChange;

    DecimalFormat formatter = new DecimalFormat("###,###");
    DecimalFormat decimalFormat = new DecimalFormat("###,###.##");

    public ItemFraudTrendCard(int totalCountOfFraud, int totalAmountOfFraud, int avgPriceOfFraud, int registeredBoard, long lastTwoMonthFraud, long contrastFraud) {
        this.totalCountOfFraud = formatter.format(totalCountOfFraud);
        this.totalAmountOfFraud = formatter.format(totalAmountOfFraud);
        this.avgPriceOfFraud = formatter.format(avgPriceOfFraud);
        this.registeredBoard = formatter.format(registeredBoard);
        this.lastTwoMonthFraud =  formatter.format(lastTwoMonthFraud);
        this.contrastFraud = formatter.format(contrastFraud);

        double chk_value =  (contrastFraud - lastTwoMonthFraud) *100 /(double)contrastFraud;

        if(chk_value > 0) { //사기 피해량이 감소 했을 떄
            this.percentOfRecentChange = decimalFormat.format(chk_value).concat("% 감소");
        }else{
            this.percentOfRecentChange = decimalFormat.format(Math.abs(chk_value)).concat("% 증가");
        }
        System.out.println(lastTwoMonthFraud *100 /(double) contrastFraud);
        System.out.println(percentOfRecentChange);
    }
}
