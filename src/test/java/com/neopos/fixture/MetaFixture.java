package com.neopos.fixture;

import com.neopos.adapter.dto.response.Meta;

public class MetaFixture {
    public static Meta gimmeMeta() {
        Meta meta = new Meta();
        meta.setTotalItems(3);
        meta.setTotalPages(1);
        meta.setCurrentPage(1);
        return meta;
    }
}
