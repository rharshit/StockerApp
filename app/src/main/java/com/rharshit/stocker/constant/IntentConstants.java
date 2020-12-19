package com.rharshit.stocker.constant;

public abstract class IntentConstants {

    private IntentConstants() {
        throw new IllegalStateException();
    }

    /**
     * Request codes
     */
    public static final int GOOGLE_SIGN_IN_CODE = 2;
    public static final int ZERODHA_REQUEST_CODE = 3;

    /**
     * Extra names
     */

    public static final String GOOGLE_SIGNOUT = "GOOGLE_SIGNOUT";
    public static final String ZERODHA_REQUEST_TOKEN = "REQUEST_TOKEN";
    public static final String ZERODHA_LOGIN_URI = "LOGIN_URI";

}
