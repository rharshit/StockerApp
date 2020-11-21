package com.rharshit.stocker.base.data;

public class BaseData {
    private boolean success;

    public BaseData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
