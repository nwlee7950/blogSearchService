# 1) 프로젝트 설명
## 1.1) 설명
카카오, 네이버 API를 이용한 블로그 검색 서비스입니다.

## 1.2) API 명세서
### 1.2.1) 블로그 검색

### URL

@GET /api/search/blog

### Parameter

##### 필수  
- String query => 검색을 원하는 질의어     
특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음

##### 선택  
- String sortValue => 결과 문서 정렬 방식         
accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy 
            

- int page => 결과 페이지 번호, 1~50 사이의 값, 기본 값 1       
            

- int size => 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10      

### 1.2.2) 인기 검색어 목록

### URL

@GET /api/search/popular

## 1.3) 디렉토리 구조
```plaintext
+---main
|   +---java
|   |   \---com
|   |       \---lsh
|   |           \---blogsearchservice
|   |               |   BlogSearchServiceApplication.java
|   |               |
|   |               +---config
|   |               |       RestTemplateConfig.java
|   |               |
|   |               +---controller
|   |               |       SearchController.java
|   |               |
|   |               +---dto
|   |               |   +---kakao
|   |               |   |       Document.java
|   |               |   |       Meta.java
|   |               |   |
|   |               |   +---naver
|   |               |   |       Item.java
|   |               |   |
|   |               |   +---request
|   |               |   |       SearchRequestDto.java
|   |               |   |
|   |               |   \---response
|   |               |           KakaoSearchResponseDto.java
|   |               |           NaverSearchResponseDto.java
|   |               |           SearchResponseDto.java
|   |               |
|   |               +---handler
|   |               |       AppExceptionHandler.java
|   |               |
|   |               +---model
|   |               |       SearchKeyword.java
|   |               |
|   |               +---repository
|   |               |       SearchKeywordRepository.java
|   |               |
|   |               +---response
|   |               |       ApiResponse.java
|   |               |
|   |               +---service
|   |               |       SearchService.java
|   |               |
|   |               \---util
|   |                       CustomStringUtils.java
|   |
|   \---resources
|       |   application.yml
|       |
|       +---static
|       \---templates
\---test
\---java
\---com
\---lsh
\---blogsearchservice
BlogSearchServiceApplicationTestsResponse.java
```