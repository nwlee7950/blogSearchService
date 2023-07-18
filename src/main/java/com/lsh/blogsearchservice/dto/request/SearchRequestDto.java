package com.lsh.blogsearchservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchRequestDto {

    private String query;
    private String sort;
    private int page;
    private int size;

    public SearchRequestDto(String query, String sort, int page, int size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

    public void changeSortForNaver(String sortValue){
        this.sort = sortValue;
    }
}
