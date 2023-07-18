package com.lsh.blogsearchservice.dto.response;

import com.lsh.blogsearchservice.dto.naver.Item;
import lombok.*;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class NaverSearchResponseDto {
    private String lastBuildDate;
    private String total;
    private String start;
    private String display;
    private List<Item> items;
}
