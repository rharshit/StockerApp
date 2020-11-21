package com.rharshit.stocker.data;

import com.google.gson.annotations.SerializedName;
import com.rharshit.stocker.base.data.BaseData;

public class Joke extends BaseData {

    @SerializedName("url")
    public String url;
    @SerializedName("value")
    public String value;

    public Joke() {
        super(true);
    }

    public void setValue(String value) {
        this.value = value;
    }
}
