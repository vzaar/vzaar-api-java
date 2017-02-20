package com.vzaar;

import java.util.Collections;
import java.util.List;

public class CategoryNode {
    private final Category category;
    private final List<CategoryNode> children;


    CategoryNode(Category category, List<CategoryNode> children) {
        this.category = category;
        this.children = Collections.unmodifiableList(children);
    }

    /**
     * Get the category at this node
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Does this node have any children
     *
     * @return true any of the children reference this category as a parent
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get the number of children, i.e. direct descendants for this category
     *
     * @return the child count
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * Get the number of descendants, i.e. the count of all nodes irrespective of depth where this
     * node is an ancestor
     *
     * @return the descendant count
     */
    public int getDescendantCount() {
        int total = 0;
        for (CategoryNode child : children) {
            total += child.getDescendantCount() + 1;
        }
        return total;
    }

    /**
     * Get the children category nodes
     *
     * @return the category nodes of direct descendants
     */
    public List<CategoryNode> getChildren() {
        return children;
    }
}
