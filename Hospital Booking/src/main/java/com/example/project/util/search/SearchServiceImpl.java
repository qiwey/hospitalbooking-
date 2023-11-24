package com.example.project.util.search;

import com.example.project.util.UtilHelper;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public <T extends Searchable> List<T> search(String keyword, List<T> list) {
        if (keyword == null || keyword.isBlank()) {
            return list;
        }
        List<BoundExtractedResult<T>> searchExtractedResult;
        searchExtractedResult = FuzzySearch.extractSorted(UtilHelper.toSlugString(keyword), list,
                x -> UtilHelper.toSlugString(x.getSearchableString()), 50);
        List<T> searched = new ArrayList<>();
        searchExtractedResult.forEach(result -> searched.add(result.getReferent()));
        return searched;
    }

    @Override
    public <T extends Searchable> Page<T> search(String keyword, List<T> list, Pageable pageable) {
        List<T> searched = search(keyword, list);
        return UtilHelper.getPage(searched, pageable);
    }
}
