package com.rharshit.stocker.base.data;

public class BaseData {
    private Boolean success;

    public BaseData(boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        if (getSuccess() == null) {
            return true;
        } else {
            return getSuccess();
        }
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
