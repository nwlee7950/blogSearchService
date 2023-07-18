package com.lsh.blogsearchservice.repository;

import com.lsh.blogsearchservice.model.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    boolean existsByQuery(String keyword);
    SearchKeyword findByQuery(String keyword);
    List<SearchKeyword> findFirst10ByOrderByCountDesc();
}