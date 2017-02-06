package com.vzaar

import spock.lang.Specification

class PagesSpec extends Specification {

    Page page

    def setup() {
        page = new Page<Category>();
        page.meta = new PageMetadata()
        page.meta.totalCount = 0
        page.data = []
    }


    def "I can list an empty page"() {
        when:
        List<Category> result = Pages.list(page)

        then:
        result.isEmpty()
    }

    def "I can iterate an empty page"() {
        when:
        Iterator<Category> result = Pages.iterator(page)

        then:
        !result.hasNext()
    }

    def "I can get an iterable for an empty page"() {
        when:
        Iterable<Category> result = Pages.iterable(page)

        then:
        !result.iterator().hasNext()
    }
}
