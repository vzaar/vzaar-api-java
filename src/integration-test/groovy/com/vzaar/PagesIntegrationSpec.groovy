package com.vzaar

class PagesIntegrationSpec extends BaseIntegrationSpec {
    private static List<Category> categories

    def setupSpec() {
        categories = (1..5).collect {
            vzaar.categories().create().withName("Category ${it}").result()
        }
    }

    def cleanupSpec() {
        categories.each {
            vzaar.categories().delete(it.id)
        }
    }

    def "I can collect all entries as a list"() {
        when:
        Page<Category> firstPage = vzaar.categories().list().withResultsPerPage(1).results()
        List<Category> result = Pages.list(firstPage)

        then:
        firstPage.totalCount >= categories.size()
        firstPage.totalCount == result.size()
        firstPage.data.size() == 1
        (result.id as Set).size() == firstPage.totalCount
    }

    def "I can collect all entries using an iterator to collate"() {
        when:
        Page<Category> firstPage = vzaar.categories().list().withResultsPerPage(1).results()
        Iterator<Category> result = Pages.iterator(firstPage)
        List<Category> list = []
        while (result.hasNext()) {
            list.add(result.next())
        }

        then:
        firstPage.totalCount >= categories.size()
        firstPage.totalCount == list.size()
        firstPage.data.size() == 1
        (list.id as Set).size() == firstPage.totalCount
    }

    def "I can collect all entries using an iterable to collate"() {
        when:
        Page<Category> firstPage = vzaar.categories().list().withResultsPerPage(1).results()
        List<Category> list = []
        for (Category category : Pages.iterable(firstPage)) {
            list.add(category)
        }

        then:
        firstPage.totalCount >= categories.size()
        firstPage.totalCount == list.size()
        firstPage.data.size() == 1
        (list.id as Set).size() == firstPage.totalCount
    }

    def "I can navigate back and for using the page object"() {
        given:
        List<Category> all = Pages.list(vzaar.categories().list().results())

        when:
        Page<Category> first = vzaar.categories().list().withResultsPerPage(2).results()

        then:
        first.data.size() == 2
        first.hasNext()
        first.hasLast()
        !first.hasPrevious()
        first.data.id as Set == [all[0], all[1]].id as Set

        when:
        Page<Category> next = first.next

        then:
        next.data.size() == 2
        next.hasNext()
        next.hasPrevious()
        next.data.id as Set == [all[2], all[3]].id as Set

        when:
        Page<Category> prev = next.previous

        then:
        prev.data.size() == 2
        prev.hasNext()
        prev.hasLast()
        !prev.hasPrevious()
        prev.data.id as Set == [all[0], all[1]].id as Set

        when:
        Page<Category> last = first.last

        then:
        last.data.size() == all.size() % 2 ? 2 : 1
        !last.hasNext()
        last.hasPrevious()
        all.last().id in (last.data.id as Set)

        when:
        first = last.first

        then:
        first.data.size() == 2
        first.hasNext()
        first.hasLast()
        !first.hasPrevious()
        first.data.id as Set == [all[0], all[1]].id as Set
    }

    def "The constructor is private"() {
        when:
        Pages pages = new Pages()

        then:
        pages
    }
}
