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
        cat1 = vzaar.categoryCreate(new CategoryCreateRequest().withName("Category 1"))
        cat2 = vzaar.categoryCreate(new CategoryCreateRequest().withName("Category 2"))
        cat11 = vzaar.categoryCreate(new CategoryCreateRequest().withName("Category 1 - 1").withParentId(cat1.id))
        cat12 = vzaar.categoryCreate(new CategoryCreateRequest().withName("Category 1 - 2").withParentId(cat1.id))
        cat111 = vzaar.categoryCreate(new CategoryCreateRequest().withName("Category 1 - 1 - 1").withParentId(cat11.id))
        categories = [cat1, cat2, cat11, cat12, cat111]
    }

    @Unroll("I can see the data from the server is correct #name")
    def "I can see the data from the server is correct"() {
        when:
        Category cat = vzaar.category(category.id)

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
        Page<Category> page = vzaar.categories(new CategoryPageRequest());

        then:
        page.totalCount == 5
        page.data.size() == 5
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
    }

    def "I can page categories"() {
        given:
        List<Category> categories = Pages.list(vzaar.categories(new CategoryPageRequest()))
        CategoryPageRequest request = new CategoryPageRequest().withResultsPerPage(2)

        when:
        Page<Category> page1 = vzaar.categories(request)

        then:
        page1.totalCount == 5
        page1.data.size() == 2
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        categories[0].id == page1.data[0].id

        when:
        Page<Category> page2 = vzaar.categories(request.withPage(2))

        then:
        page2.totalCount == 5
        page2.data.size() == 2
        page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        categories[2].id == page2.data[0].id

        when:
        Page<Category> page3 = vzaar.categories(request.withPage(3))

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
        Page<Category> page = vzaar.categories(new CategoryPageRequest().withLevels(1));

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
        Page<Category> page = vzaar.categories(new CategoryPageRequest().withLevels(2));

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
        Page<Category> page = vzaar.categories(new CategoryPageRequest().withIds([cat11.id, cat2.id]));

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
        CategoryPageRequest request = new CategoryPageRequest().withResultsPerPage(2).withSortByAttribute(attribute).withSortDirection(SortDirection.asc)

        when:
        List<Category> categories = Pages.list(vzaar.categories(request))

        then:
        categories.size() > 0
        categories.collect(map) == categories.collect(map).sort()

        when:
        categories = Pages.list(vzaar.categories(request.withSortDirection(SortDirection.desc)))

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
        Page<Category> page = vzaar.categories(cat11.id, new CategoryPageRequest());

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
        List<Category> categories = Pages.list(vzaar.categories(cat1.id, new CategoryPageRequest()))
        CategoryPageRequest request = new CategoryPageRequest().withResultsPerPage(2)

        when:
        Page<Category> page1 = vzaar.categories(cat1.id, request)

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
        Page<Category> page = vzaar.categories(cat11.id, new CategoryPageRequest().withLevels(1));

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
        Page<Category> page = vzaar.categories(cat11.id, new CategoryPageRequest().withIds([cat11.id, cat2.id]));

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
        Category category = vzaar.categoryCreate(new CategoryCreateRequest().withName("Updatable category"))

        then:
        category.name == 'Updatable category'
        category.depth == 0
        category.parentId == null

        when:
        category = vzaar.categoryUpdate(category.id, new CategoryUpdateRequest()
                .withName("Moved category")
                .withParentId(cat12.id))

        then:
        category.name == 'Moved category'
        category.depth == 2
        category.parentId == cat12.id

        when:
        category = vzaar.categoryUpdate(category.id, new CategoryUpdateRequest()
                .withName("Root category")
                .withMoveToRoot(true))

        then:
        category.name == 'Root category'
        category.depth == 0
        category.parentId == null

        cleanup:
        vzaar.categoryDelete(category.id)
    }

    def "I can delete a category"() {
        given:
        Category category = vzaar.categoryCreate(new CategoryCreateRequest().withName("Deletable category"))

        when:
        vzaar.categoryDelete(category.id)
        vzaar.category(category.id)

        then:
        VzaarServerException exception = thrown(VzaarServerException)
        exception.statusCode == 404
    }
}
