package com.lsh.blogsearchservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor()
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseDto {
    private KakaoSearchResponseDto kakaoSearchResponseDto;
    private NaverSearchResponseDto naverSearchResponseDto;
}
