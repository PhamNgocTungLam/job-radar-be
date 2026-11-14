package com.job.common.res;

import com.github.pagehelper.PageInfo;
import lombok.Getter;

import java.util.List;

// PageHelper đánh pageNum từ 1, API Spec yêu cầu page từ 0 nên of(PageInfo) phải trừ 1.
@Getter
public class PageRes<T> {

    private final List<T> items;
    private final int page;
    private final int size;
    private final long total;

    private PageRes(List<T> items, int page, int size, long total) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public static <T> PageRes<T> of(PageInfo<T> pageInfo) {
        return new PageRes<>(pageInfo.getList(), pageInfo.getPageNum() - 1, pageInfo.getPageSize(), pageInfo.getTotal());
    }

    public static <T> PageRes<T> of(List<T> items, int page, int size, long total) {
        return new PageRes<>(items, page, size, total);
    }
}
