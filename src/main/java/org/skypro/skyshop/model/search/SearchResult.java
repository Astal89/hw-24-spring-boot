package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class SearchResult {
    private final UUID id;
    private final String name;
    private final String contentType;

    public SearchResult(UUID id, String name, String contentType) {
        this.contentType = contentType;
        this.id = id;
        this.name = name;
    }

    public static SearchResult fromSearchable(Searchable item) {
        return new SearchResult(item.getId(), item.getName(), item.getContentType());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getContentType() {
        return contentType;
    }
}
