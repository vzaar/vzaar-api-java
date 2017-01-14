package com.vzaar;

import java.util.Map;

public class PageMetadata {
    private static final String LINK_FIRST = "first";
    private static final String LINK_NEXT = "next";
    private static final String LINK_PREVIOUS = "previous";
    private static final String LINK_LAST = "last";

    private int totalCount;
    private Map<String, String> links;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean hasFirstLink() {
        return getFirstLink() != null;
    }

    public String getFirstLink() {
        return links.get(LINK_FIRST);
    }

    public boolean hasNextLink() {
        return getNextLink() != null;
    }

    public String getNextLink() {
        return links.get(LINK_NEXT);
    }

    public boolean hasPreviousLink() {
        return getPreviousLink() != null;
    }

    public String getPreviousLink() {
        return links.get(LINK_PREVIOUS);
    }

    public boolean hasLastLink() {
        return getLastLink() != null;
    }

    public String getLastLink() {
        return links.get(LINK_LAST);
    }
}
