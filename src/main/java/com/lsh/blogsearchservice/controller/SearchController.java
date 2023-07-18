package com.lsh.blogsearchservice.controller;

import com.lsh.blogsearchservice.dto.request.SearchRequestDto;
import com.lsh.blogsearchservice.dto.response.SearchResponseDto;
import com.lsh.blogsearchservice.response.ApiResponse;
import com.lsh.blogsearchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    
    private final SearchService searchService;

    @GetMapping("/blog")
    public ApiResponse<?> searchBlog(
            @RequestParam @NotBlank String query,
            @RequestParam (defaultValue = "accuracy") String sortValue,
            @RequestParam (defaultValue = "1") @Min(1) @Max(50) int page,
            @RequestParam (defaultValue = "10") @Min(1) @Max(50) int size
    ) throws UnsupportedEncodingException {
        SearchRequestDto searchDto = new SearchRequestDto(query, sortValue, page, size);
        SearchResponseDto searchResponseDto = searchService.searchBlog(searchDto);
        return ApiResponse.success(searchResponseDto);
    }

    @GetMapping("/popular")
    public ApiResponse<?> getPopularTop10()
    {
        return ApiResponse.success(searchService.getPopularTop10());
    }
}
