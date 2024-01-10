package com.neopos.adapters.utils;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagination<T> {
    private final static int DEFAULT_PAGE_NUMBER = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    public PagedListHolder<T> build(Integer pageNumber, Integer pageSize, List<T> object) {
        int queryPageNumber;
        int queryPageSize;
        PagedListHolder<T> page = new PagedListHolder<>(object);

        if(pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize != null && pageSize > 0 && pageSize < 30) {
            queryPageSize = pageSize;
        } else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        page.setPage(queryPageNumber);
        page.setPageSize(queryPageSize);

        return page;
    }

    public Map<String, String> buildPageLinks(PagedListHolder<T> page) {
        Map<String, String> links = new HashMap<>();


        links.put("self", "");
        links.put("prev", "");
        links.put("next", "");
        links.put("first", "");
        links.put("last", Integer.toString(page.getPageCount()));

        return links;
    }
}
