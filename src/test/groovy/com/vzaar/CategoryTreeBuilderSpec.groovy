package com.vzaar

import spock.lang.Specification

class CategoryTreeBuilderSpec extends Specification {

    def "I can build a tree with leafs"() {
        given:
            List<Category> categories = [
              new Category(id: 2, parentId: null, name: 'B'),
              new Category(id: 1, parentId: null, name: 'A'),
              new Category(id: 11, parentId: 1, name: 'AA'),
              new Category(id: 12, parentId: 1, name: 'AB'),
              new Category(id: 112, parentId: 11, name: 'AAB'),
              new Category(id: 111, parentId: 11, name: 'AAA'),
              new Category(id: 3, parentId: null, name: 'C'),
              new Category(id: 31, parentId: 3, name: 'CA'),
              new Category(id: 32, parentId: 3, name: 'CB'),
              new Category(id: 33, parentId: 3, name: 'CC'),
            ];

        when:
        List<CategoryNode> tree = CategoryTreeBuilder.build(categories)

        then:
        tree.size() == 3
        tree[0].category.id == 1
        tree[0].hasChildren()
        tree[0].childCount == 2
        tree[0].descendantCount == 4
        tree[0].children[0].category.id == 11
        tree[0].children[0].hasChildren()
        tree[0].children[0].childCount == 2
        tree[0].children[0].descendantCount == 2
        tree[0].children[0].children[0].category.id == 111
        !tree[0].children[0].children[0].hasChildren()
        tree[0].children[0].children[1].childCount == 0
        tree[0].children[0].children[1].descendantCount == 0
        tree[0].children[0].children[1].category.id == 112

        tree[0].children[1].category.id == 12
        !tree[0].children[1].hasChildren()
        tree[0].children[1].childCount == 0
        tree[0].children[1].descendantCount == 0

        tree[1].category.id == 2
        !tree[1].hasChildren()
        tree[1].childCount == 0
        tree[1].descendantCount == 0
        tree[1].children.size() == 0

        tree[2].category.id == 3
        tree[2].hasChildren()
        tree[2].childCount == 3
        tree[2].descendantCount == 3

        tree[2].children[0].category.id == 31
        tree[2].children[0].childCount == 0
        tree[2].children[0].descendantCount == 0
        tree[2].children[1].category.id == 32
        tree[2].children[1].childCount == 0
        tree[2].children[1].descendantCount == 0
        tree[2].children[2].category.id == 33
        tree[2].children[2].childCount == 0
        tree[2].children[2].descendantCount == 0
    }
}
