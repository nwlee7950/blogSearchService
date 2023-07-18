package com.lsh.blogsearchservice.dto.response;

import lombok.*;

@NoArgsConstructor()
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchResponseDto {
    private KakaoSearchResponseDto kakaoSearchResponseDto;
    private NaverSearchResponseDto naverSearchResponseDto;
}
