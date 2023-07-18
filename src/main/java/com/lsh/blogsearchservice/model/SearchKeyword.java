package com.lsh.blogsearchservice.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity(name = "search_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;
    private Integer count;

    public SearchKeyword(String query, Integer count){
        this.query = query;
        this.count = count;
    }

    public void upCount() {
        this.count++;
    }
}
