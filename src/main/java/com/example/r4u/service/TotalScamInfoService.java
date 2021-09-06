package com.example.r4u.service;

import com.example.r4u.domain.TotalScamInfo;
import com.example.r4u.repository.TotalScamInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TotalScamInfoService {

    @Autowired
    TotalScamInfoRepository totalScamInfoRepository = new TotalScamInfoRepository();
    // 전체 사기 피해 금액 리턴 메서드
//    private List<TotalScamInfo> GetTotalScamInfo(){
//
//    }
}
