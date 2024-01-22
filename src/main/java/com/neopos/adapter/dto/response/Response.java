package com.neopos.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"data", "links", "meta"})
public class Response<T> extends ResponseWithLinks {
    private T data;
    private Meta meta;

    public Response(T data, Meta meta, Map<String, String> _links) {
        super(_links);
        this.data = data;
        this.meta = meta;
    }
}
