package com.vzaar;

import java.util.List;

public class CategoryListRequest extends PageableRequest<CategoryListRequest> {

    private Integer levels;
    private List<Integer> ids;

    public CategoryListRequest() {
        super(CategoryListRequest.class);
    }

    public CategoryListRequest withLevels(Integer levels) {
        if (levels != null && levels < 1) {
            throw new IllegalArgumentException(String.format("levels must be 1 or greater, received=[%s]", levels));
        }

        this.levels = levels;
        return this;
    }

    public CategoryListRequest withIds(List<Integer> ids) {
        this.ids = ids;
//        if (ids == null || ids.isEmpty()) {
//            this.ids = null;
//        } else {
//            StringBuilder sb = new StringBuilder();
//            for (Integer id : ids) {
//                if (sb.length() > 0) {
//                    sb.append(",");
//                }
//                sb.append(id);
//            }
//            this.ids = sb.toString();
//        }
        return this;
    }
}
