package com.vzaar;

import java.time.ZonedDateTime;

public class Category {
    private int id;
    private int accountId;
    private int userId;
    private String name;
    private String description;
    private Integer parentId;
    private int depth;
    private int nodeChildrenCount;
    private int treeChildrenCount;
    private int nodeVideoCount;
    private int treeVideoCount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public int getDepth() {
        return depth;
    }

    public int getNodeChildrenCount() {
        return nodeChildrenCount;
    }

    public int getTreeChildrenCount() {
        return treeChildrenCount;
    }

    public int getNodeVideoCount() {
        return nodeVideoCount;
    }

    public int getTreeVideoCount() {
        return treeVideoCount;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
