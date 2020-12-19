package com.rharshit.stocker.constant;

public abstract class DBConstants {

    private DBConstants() {
        throw new IllegalStateException();
    }

    /**
     * Application wide
     */
    public static final String REF_APP = "appData";
    public static final String REF_USERS = "users";
    public static final String REF_USER_DATA = "userData";

    public static final String ZERODHA_SHARED_PREF = "spZerodha";
}
