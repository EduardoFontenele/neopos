package com.neopos.adapter.utils;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LinkBuilder {

    @Value("${application.base-url}")
    private String baseUrl;

    public Map<String, String> buildSelfLink(String path, String id) {
        Map<String, String> links = new LinkedHashMap<>();
        links.put("self", String.format("%s/%s/%s", baseUrl, path, id));
        links.put("rel", String.format("%s/%s", baseUrl, path));
        return links;
    }


    public Map<String, String> buildPageLinks(String path, int totalPages, int queryPageNumber, int queryPageSize) {
        Map<String, String> links;

        if(totalPages > 0) {
            links = new LinkedHashMap<>();
            links.put("self", String.format("%s/%s?page-number=%d&page-size=%d", baseUrl, path, queryPageNumber, queryPageSize));
            links.put("first", String.format("%s/%s?page-number=%d&page-size=%d", baseUrl, path, 1, queryPageSize));
            links.put("last", String.format("%s/%s?page-number=%d&page-size=%d", baseUrl, path, totalPages, queryPageSize));

            if(queryPageNumber > totalPages) {
                throw new RuntimeException("Page-number bigger than total pages available");
            }

            if(totalPages > 1 && queryPageNumber > 1) {
                links.put("prev", String.format("%s/%s?page-number=%d&page-size=%d", baseUrl, path, queryPageNumber - 1, queryPageSize));
            }

            if(queryPageNumber < totalPages) {
                links.put("next", String.format("%s/%s?page-number=%d&page-size=%d", baseUrl, path, queryPageNumber + 1, queryPageSize));
            }

            return links;
        }

        return new HashMap<>();
    }
}
