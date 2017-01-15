package com.vzaar

import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class CategoryIntegrationSpec extends BaseIntegrationSpec {


    def "I can list recipes"() {
        when:
        Page<Category> page = vzaar.categories(new CategoryPageRequest());

        then:
        with(page) {
            page.meta.totalCount > 2
        }

        when:
        println page.data*.id.subList(0, 2)
        page = vzaar.categories(new CategoryPageRequest().withIds(page.data*.id.subList(0, 2)).withResultsPerPage(1))

        then:
        println page.meta.getLastLink()
        with(page) {
            page.meta.totalCount == 2
        }
    }
}
