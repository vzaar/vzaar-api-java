package com.vzaar;

import java.util.Map;

public class PageMetadata {
    private static final String LINK_FIRST = "first";
    private static final String LINK_NEXT = "next";
    private static final String LINK_PREVIOUS = "previous";
    private static final String LINK_LAST = "last";

    private int totalCount;
    private Map<String, String> links;

    int getTotalCount() {
        return totalCount;
    }

    /**
     * Does the metadata contain a link for the first page
     * @return true if it does
     */
    boolean hasFirstLink() {
        return getFirstLink() != null;
    }

    String getFirstLink() {
        return links.get(LINK_FIRST);
    }

    boolean hasNextLink() {
        return getNextLink() != null;
    }

    String getNextLink() {
        return links.get(LINK_NEXT);
    }

    boolean hasPreviousLink() {
        return getPreviousLink() != null;
    }

    String getPreviousLink() {
        return links.get(LINK_PREVIOUS);
    }

    boolean hasLastLink() {
        return getLastLink() != null;
    }

    String getLastLink() {
        return links.get(LINK_LAST);
    }
}
