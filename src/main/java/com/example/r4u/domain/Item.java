package com.example.r4u.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Item {

    public String name;

    public Long id;

    public String category;

    public String platform;

    public String timestamp;

    public int price;

    public String outline;


}
