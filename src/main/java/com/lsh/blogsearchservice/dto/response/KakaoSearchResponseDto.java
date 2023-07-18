package com.lsh.blogsearchservice.dto.response;

import com.lsh.blogsearchservice.dto.kakao.Document;
import com.lsh.blogsearchservice.dto.kakao.Meta;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class KakaoSearchResponseDto {
    private Meta meta;
    private List<Document> documents;
}
