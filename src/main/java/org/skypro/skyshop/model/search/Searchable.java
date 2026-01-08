package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String searchTerm();

    String getContentType();

    UUID getId();

    default String getStringRepresentation() {
        return searchTerm() + " - " + getContentType();
    }
}
