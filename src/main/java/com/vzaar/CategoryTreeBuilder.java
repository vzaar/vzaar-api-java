package com.vzaar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class to build a structured tree of categories.
 */
public final class CategoryTreeBuilder {
    private CategoryTreeBuilder() {

    }

    /**
     * Build a structured tree representing the categories where the children, including the root nodes are
     * sorted by the category name
     * @param categories the categories
     * @return the root nodes, i.e. the nodes without a parent id
     */
    public static List<CategoryNode> build(Iterable<Category> categories) {
        return build(categories, (o1, o2) -> {
            int result = o1.getName().compareToIgnoreCase(o2.getName());
            return result == 0 ? o1.getId() - o2.getId() : result;
        });
    }

    /**
     * Build a structured tree representing the categories where the children.
     * @param categories the categories
     * @param order a conmparator to determine the order in which to sort the children, including the root nodes
     * @return the root nodes, i.e. the nodes without a parent id
     */
    public static List<CategoryNode> build(Iterable<Category> categories, Comparator<Category> order) {
        Map<Integer, List<Category>> parentIdMap = new HashMap<>();
        for (Category category : categories) {
            List<Category> children = parentIdMap.get(category.getParentId());
            if (children == null) {
                children = new ArrayList<>();
                parentIdMap.put(category.getParentId(), children);
            }
            children.add(category);
        }

        return buildChildren(null, parentIdMap, order);
    }

    private static List<CategoryNode> buildChildren(Category category, Map<Integer, List<Category>> parentIdMap, Comparator<Category> order) {
        List<Category> children = parentIdMap.get(category == null ? null : category.getId());

        if (children == null) {
            children = Collections.emptyList();
        }

        return children.stream()
                .sorted(order)
                .map(child -> new CategoryNode(child, buildChildren(child, parentIdMap, order)))
                .collect(Collectors.toList());
    }
}
