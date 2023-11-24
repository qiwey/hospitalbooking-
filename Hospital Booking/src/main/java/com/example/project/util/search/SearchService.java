package com.example.project.util.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    <T extends Searchable> List<T> search(String keyword, List<T> list);

    <T extends Searchable> Page<T> search(String keyword, List<T> list, Pageable pageable);
}
