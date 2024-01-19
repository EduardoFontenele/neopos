package com.neopos.adapters.utils;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Pagination {
    public final static int DEFAULT_PAGE_NUMBER = 1;
    public final static int DEFAULT_PAGE_SIZE = 10;

    public static Integer validatePageNumber(Integer pageNumber) {
        if(pageNumber != null && pageNumber > 0) {
            return pageNumber;
        } else {
            return DEFAULT_PAGE_NUMBER;
        }
    }

    public static Integer validatePageSize(Integer pageSize) {
        int queryPageSize;

        if(pageSize != null && pageSize > 0) {
            if (pageSize <= 50) {
                queryPageSize = pageSize;
            } else {
                queryPageSize = 50;
            }
        } else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        return queryPageSize;
    }

}
