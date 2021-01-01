package com.rharshit.stocker.data;

import com.rharshit.stocker.base.data.BaseData;

public class PaginationData extends BaseData {
    private int limit;
    private int offset;
    private int count;
    private int total;

    public PaginationData(boolean success) {
        super(success);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
