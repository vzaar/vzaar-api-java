package com.vzaar

import spock.lang.Unroll

class CategoryIntegrationSpec extends BaseIntegrationSpec {
    private static Category cat1
    private static Category cat11
    private static Category cat12
    private static Category cat111
    private static Category cat2

    private static List<Category> categories

    def setupSpec() {
        cat1 = vzaar.categories().create().withName("Category 1").result()
        cat2 = vzaar.categories().create().withName("Category 2").result()
        cat11 = vzaar.categories().create().withName("Category 1 - 1").withParentId(cat1.id).result()
        cat12 = vzaar.categories().create().withName("Category 1 - 2").withParentId(cat1.id).result()
        cat111 = vzaar.categories().create().withName("Category 1 - 1 - 1").withParentId(cat11.id).result()
        categories = [cat1, cat2, cat11, cat12, cat111]
    }

    @Unroll("I can see the data from the server is correct #name")
    def "I can see the data from the server is correct"() {
        when:
        Category cat = vzaar.categories().get(category.id)

        then:
        cat.name == name
        cat.depth == depth
        cat.parentId == parentId
        cat.treeChildrenCount == treeChildren
        cat.nodeChildrenCount == nodeChildren

        where:
        category | depth | nodeChildren | treeChildren | parentId | name
        cat1     | 0     | 2            | 3            | null     | 'Category 1'
        cat11    | 1     | 1            | 1            | cat1.id  | 'Category 1 - 1'
        cat12    | 1     | 0            | 0            | cat1.id  | 'Category 1 - 2'
        cat111   | 2     | 0            | 0            | cat11.id | 'Category 1 - 1 - 1'
        cat2     | 0     | 0            | 0            | null     | 'Category 2'
    }

    def "I can list categories"() {
        when:
        Page<Category> page = vzaar.categories().list().results();

        then:
        page.totalCount == 5
        page.data.size() == 5
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
    }

    def "I can page categories"() {
        given:
        List<Category> categories = Pages.list(vzaar.categories().list().results())
        CategoryPageRequest request = vzaar.categories().list().withResultsPerPage(2)

        when:
        Page<Category> page1 = request.results()

        then:
        page1.totalCount == 5
        page1.data.size() == 2
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        categories[0].id == page1.data[0].id

        when:
        Page<Category> page2 = request.withPage(2).results()

        then:
        page2.totalCount == 5
        page2.data.size() == 2
        page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        categories[2].id == page2.data[0].id

        when:
        Page<Category> page3 = request.withPage(3).results()

        then:
        page3.totalCount == 5
        page3.data.size() == 1
        !page3.hasNext()
        page3.hasPrevious()
        page3.hasLast()
        categories[4].id == page3.data[0].id
    }

    def "I can restrict categories to 1 level"() {
        when:
        Page<Category> page = vzaar.categories().list().withLevels(1).results();

        then:
        page.totalCount == 2
        page.data.size() == 2
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
        page.data.id as Set == [cat1, cat2].id as Set
    }

    def "I can restrict categories to 2 levels"() {
        when:
        Page<Category> page = vzaar.categories().list().withLevels(2).results();

        then:
        page.totalCount == 4
        page.data.size() == 4
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
        page.data.id as Set == [cat1, cat2, cat11, cat12].id as Set
    }

    def "I can query categories by id"() {
        when:
        Page<Category> page = vzaar.categories().list().withIds([cat11.id, cat2.id]).results();

        then:
        page.totalCount == 2
        page.data.size() == 2
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
        page.data.id as Set == [cat11, cat2].id as Set
    }

    @Unroll("I can sort categories by #attribute")
    def "I can sort categories by attributes"() {
        given:
        CategoryPageRequest request = vzaar.categories().list().withResultsPerPage(2).withSortByAttribute(attribute).withSortDirection(SortDirection.asc)

        when:
        List<Category> categories = Pages.list(request.results())

        then:
        categories.size() > 0
        categories.collect(map) == categories.collect(map).sort()

        when:
        categories = Pages.list(request.withSortDirection(SortDirection.desc).results())

        then:
        categories.size() > 0
        categories.collect(map) == categories.collect(map).sort().reverse()

        where:
        attribute | map
        'id'      | { it.id }
        'name'    | { it.name }
    }

    def "I can list subcategories"() {
        when:
        Page<Category> page = vzaar.categories().subtree(cat11.id).results()

        then:
        page.totalCount == 2
        page.data.size() == 2
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
        page.data.id as Set == [cat11, cat111].id as Set

    }

    def "I can page subcategories"() {
        given:
        List<Category> categories = Pages.list(vzaar.categories().subtree(cat1.id).results())

        when:
        Page<Category> page1 = vzaar.categories().subtree(cat1.id).withResultsPerPage(2).results()

        then:
        categories.size() == 4
        page1.totalCount == 4
        page1.data.size() == 2
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        categories[0].id == page1.data[0].id

        when:
        Page<Category> page2 = page1.next

        then:
        page2.totalCount == 4
        page2.data.size() == 2
        !page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        categories[2].id == page2.data[0].id
    }

    def "I can restrict subcategories to 1 level"() {
        when:
        Page<Category> page = vzaar.categories().subtree(cat11.id).withLevels(1).results()

        then:
        page.totalCount == 1
        page.data.size() == 1
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
        page.data.id as Set == [cat11].id as Set
    }

    def "I can query subcategories by id"() {
        when:
        Page<Category> page = vzaar.categories().subtree(cat11.id).withIds([cat11.id, cat2.id]).results()

        then:
        page.totalCount == 1
        page.data.size() == 1
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
        page.data.id as Set == [cat11].id as Set
    }

    def "I can update a category"() {
        when:
        Category category = vzaar.categories().create().withName("Updatable categories").result()

        then:
        category.name == 'Updatable categories'
        category.depth == 0
        category.parentId == null

        when:
        category = vzaar.categories().update(category.id)
                .withName("Moved categories")
                .withParentId(cat12.id)
                .result()

        then:
        category.name == 'Moved categories'
        category.depth == 2
        category.parentId == cat12.id

        when:
        category = vzaar.categories().update(category.id)
                .withName("Root categories")
                .withMoveToRoot(true)
                .result()

        then:
        category.name == 'Root categories'
        category.depth == 0
        category.parentId == null

        cleanup:
        vzaar.categories().delete(category.id)
    }

    def "I can delete a category"() {
        given:
        Category category = vzaar.categories().create().withName("Deletable categories").result()

        when:
        vzaar.categories().delete(category.id)
        vzaar.categories().get(category.id)

        then:
        VzaarServerException exception = thrown(VzaarServerException)
        exception.statusCode == 404
    }
}
