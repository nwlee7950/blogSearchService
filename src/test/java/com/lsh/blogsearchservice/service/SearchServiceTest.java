package com.lsh.blogsearchservice.service;

import com.lsh.blogsearchservice.model.SearchKeyword;
import com.lsh.blogsearchservice.repository.SearchKeywordRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

@SpringBootTest
class SearchServiceTest {
    @Autowired
    SearchKeywordRepository searchKeywordRepository;

    @Test
    void insertDataAndGetPopular10(){
        Random rn = new Random();
        // 랜덤 문자열 길이
        int strLength = 3;
        int dataSize = 100000;

        StopWatch stopWatch = new StopWatch();
        stopWatch.reset();
        stopWatch.start();
        // 데이터 10만개 삽입
        for(int i = 0; i < dataSize; i++){
            String randomStr = RandomStringUtils.random(strLength, false, true);
            // 이미 저장된 키워드면 count++, 아니라면 카운트 1로 세팅하고 저장
            if (searchKeywordRepository.existsByQuery(randomStr)){
                SearchKeyword searchKeyword = searchKeywordRepository.findByQuery(randomStr);
                searchKeyword.upCount();
                searchKeywordRepository.save(searchKeyword);
            } else {
                SearchKeyword searchKeyword = SearchKeyword
                        .builder()
                        .query(randomStr)
                        .count(1)
                        .build();
                searchKeywordRepository.save(searchKeyword);
            }
        }

        stopWatch.stop();
        System.out.println("데이터 삽입에 소요된 수행시간 :  " + stopWatch.getTime() + " ms");

        stopWatch.reset();
        stopWatch.start();
        List<SearchKeyword> searchKeywordList = searchKeywordRepository.findFirst10ByOrderByCountDesc();
        for(int i = 1; i <= searchKeywordList.size(); i++){
            System.out.println(i + "번째 인기 검색어 : " + searchKeywordList.get(i - 1).getQuery() + ", 검색 횟수 : " + searchKeywordList.get(i - 1).getCount());
        }
        stopWatch.stop();
        System.out.println("인기 검색어 TOP 10을 가져오는데 걸린 시간 : " +  stopWatch.getTime() + " ms");
    }
}