package com.example.r4u.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemFraudTrendCard {

    public int totalCountOfFraud;

    public int totalAmountOfFraud;

    public int avgPriceOfFraud;

    public int registeredBoard;

    public ItemFraudTrendCard(int totalCountOfFraud, int totalAmountOfFraud, int avgPriceOfFraud, int registeredBoard) {
        this.totalCountOfFraud = totalCountOfFraud;
        this.totalAmountOfFraud = totalAmountOfFraud;
        this.avgPriceOfFraud = avgPriceOfFraud;
        this.registeredBoard = registeredBoard;
    }
}
