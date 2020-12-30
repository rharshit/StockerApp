package com.rharshit.stocker.data;

import com.rharshit.stocker.base.data.BaseData;

import java.util.List;

public class BaseMarketstackData<T> extends BaseData {
    private PaginationData pagination;
    private List<T> data;
    private MarketstackErrorData error;

    public BaseMarketstackData(boolean success) {
        super(success);
    }

    public PaginationData getPagination() {
        return pagination;
    }

    public void setPagination(PaginationData pagination) {
        this.pagination = pagination;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public MarketstackErrorData getError() {
        return error;
    }

    public void setError(MarketstackErrorData error) {
        this.error = error;
    }

    public int getLimit() {
        PaginationData paginationData = getPagination();
        if (paginationData == null) {
            return 0;
        }
        return paginationData.getLimit();
    }

    public int getCount() {
        PaginationData paginationData = getPagination();
        if (paginationData == null) {
            return 0;
        }
        return paginationData.getCount();
    }

    public int getTotal() {
        PaginationData paginationData = getPagination();
        if (paginationData == null) {
            return 0;
        }
        return paginationData.getTotal();
    }

    public int getOffset() {
        PaginationData paginationData = getPagination();
        if (paginationData == null) {
            return 0;
        }
        return paginationData.getOffset();
    }
}
