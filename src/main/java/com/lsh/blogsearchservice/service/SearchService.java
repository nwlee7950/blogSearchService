package com.lsh.blogsearchservice.service;

import com.lsh.blogsearchservice.dto.response.KakaoSearchResponseDto;
import com.lsh.blogsearchservice.dto.request.SearchRequestDto;
import com.lsh.blogsearchservice.dto.response.NaverSearchResponseDto;
import com.lsh.blogsearchservice.dto.response.SearchResponseDto;
import com.lsh.blogsearchservice.model.SearchKeyword;
import com.lsh.blogsearchservice.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.util.*;

import static com.lsh.blogsearchservice.util.CustomStringUtils.mapToUrlParam;

@Service
@RequiredArgsConstructor
public class SearchService {
    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;

    @Value("${NAVER_CLIENT_ID}")
    private String NAVER_CLIENT_ID;

    @Value("${NAVER_CLIENT_SECRET}")
    private String NAVER_CLIENT_SECRET;
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/search/blog";
    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/blog.json";
    private final RestTemplate restTemplate;
    private final SearchKeywordRepository searchKeywordRepository;

    @Transactional
    public SearchResponseDto searchBlog(SearchRequestDto searchRequestDto) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        HttpEntity<String> requestEntity = buildKakaoRequestEntity();
        String url = buildKakaoUrl(searchRequestDto);

        if(!"accuracy".equals(searchRequestDto.getSort()) && !"recency".equals(searchRequestDto.getSort())){
            throw new InvalidParameterException("정렬 형식이 맞지 않습니다.");
        }

        // getForObject, getForEntity는 헤더를 추가할 수 없기에 exchange 사용
        ResponseEntity<KakaoSearchResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, KakaoSearchResponseDto.class);

        // 카카오 API 장애가 발생한 경우
        if(Integer.parseInt(response.getStatusCode().toString().substring(0, 3)) >= 500){
            // 네이버 API 호출
            if("accuracy".equals(searchRequestDto.getSort())){
                searchRequestDto.changeSortForNaver("sim");
            } else {
                searchRequestDto.changeSortForNaver("date");
            }
            requestEntity = buildNaverRequestEntity();
            url = buildNaverUrl(searchRequestDto);

            ResponseEntity<NaverSearchResponseDto> naverResponse = restTemplate.exchange(url, HttpMethod.GET, requestEntity, NaverSearchResponseDto.class);

            // 이미 저장된 키워드면 count++, 아니라면 카운트 1로 세팅하고 저장
            if (isExistQuery(searchRequestDto.getQuery())){
                SearchKeyword searchKeyword = findByQuery(searchRequestDto.getQuery());
                searchKeyword.upCount();
            } else {
                SearchKeyword searchKeyword = SearchKeyword
                        .builder()
                        .query(searchRequestDto.getQuery())
                        .count(1)
                        .build();
                searchKeywordRepository.save(searchKeyword);
            }

            searchResponseDto.setNaverSearchResponseDto(naverResponse.getBody());
        } else {
            // 카카오 API 성공한 경우

            // 이미 저장된 키워드면 count++, 아니라면 카운트 1로 세팅하고 저장
            if (isExistQuery(searchRequestDto.getQuery())){
                SearchKeyword searchKeyword = findByQuery(searchRequestDto.getQuery());
                searchKeyword.upCount();
            } else {
                SearchKeyword searchKeyword = SearchKeyword
                        .builder()
                        .query(searchRequestDto.getQuery())
                        .count(1)
                        .build();
                searchKeywordRepository.save(searchKeyword);
            }
            searchResponseDto.setKakaoSearchResponseDto(response.getBody());
        }
        return searchResponseDto;
    }

    private String buildKakaoUrl(SearchRequestDto searchRequestDto) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("query", searchRequestDto.getQuery());
        reqMap.put("sort", searchRequestDto.getSort());
        reqMap.put("page", searchRequestDto.getPage());
        reqMap.put("size", searchRequestDto.getSize());
        return KAKAO_API_URL + "?" + mapToUrlParam(reqMap);
    }

    private String buildNaverUrl(SearchRequestDto searchRequestDto) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("query", searchRequestDto.getQuery());
        reqMap.put("display", searchRequestDto.getSize());
        reqMap.put("start", searchRequestDto.getPage());
        reqMap.put("sort", searchRequestDto.getSort());
        return NAVER_API_URL + "?" + mapToUrlParam(reqMap);
    }

    private HttpEntity<String> buildKakaoRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<String> buildNaverRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.set("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
        return new HttpEntity<>(headers);
    }

    public boolean isExistQuery(String query) {
        return searchKeywordRepository.existsByQuery(query);
    }

    public SearchKeyword findByQuery(String keyword) {
        return searchKeywordRepository.findByQuery(keyword);
    }

    public List<SearchKeyword> getPopularTop10() {
        return searchKeywordRepository.findFirst10ByOrderByCountDesc();
    }
}
