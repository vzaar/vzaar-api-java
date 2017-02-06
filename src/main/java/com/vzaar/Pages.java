package com.vzaar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Pages {
    private Pages() {

    }

    public static <T> List<T> list(Page<T> firstPage) {
        if (firstPage.getTotalCount() == 0) {
            return Collections.emptyList();
        }

        List<T> all = new ArrayList<>(firstPage.getTotalCount());
        all.addAll(firstPage.getData());

        Page<T> current = firstPage;
        while (current.hasNext()) {
            current = current.getNext();
            all.addAll(current.getData());
        }

        return all;
    }

    public static <T> Iterator<T> iterator(Page<T> page) {
        if (page.getTotalCount() == 0) {
            return Collections.emptyIterator();
        }

        return new PageConcatIterator<>(page);
    }


    public static <T> Iterable<T> iterable(Page<T> page) {
        return () -> Pages.iterator(page);
    }

    private static final class PageConcatIterator<T> implements Iterator<T> {

        private Page<T> currentPage;
        private Iterator<T> currentIterator;

        PageConcatIterator(Page<T> current) {
            this.currentPage = current;
            this.currentIterator = current.getData().iterator();
        }

        @Override
        public boolean hasNext() {
            return currentIterator.hasNext() || currentPage.hasNext();
        }

        @Override
        public T next() {
            if (!currentIterator.hasNext() && currentPage.hasNext()) {
                currentPage = currentPage.getNext();
                currentIterator = currentPage.getData().iterator();
            }

            return currentIterator.next();
        }
    }
}
