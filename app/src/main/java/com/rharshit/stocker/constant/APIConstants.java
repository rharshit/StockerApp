package com.rharshit.stocker.constant;

import com.rharshit.stocker.BuildConfig;

public abstract class APIConstants {

    private APIConstants() {
        throw new IllegalStateException();
    }

    /**
     * Base URLs
     */
    public static final String BASE_URL_CHUCK = "https://api.chucknorris.io/";

    /**
     * endpoints
     */

    public static final String GET_CHUCK_NORRIS_JOKES = "/jokes/random";

    /**
     * Query params
     */

    public static final String ZERODHA_LOGIN_STATUS = "status";
    public static final String ZERODHA_LOGIN_REQUEST_TOKEN = "request_token";

    /**
     * Keys
     */
    public static final String ZERODHA_API_KEY = BuildConfig.ZERODHA_API_KEY;
    public static final String ZERODHA_API_SECRET = BuildConfig.ZERODHA_API_SECRET;
    public static final String ZERODHA_USER_ID = BuildConfig.ZERODHA_USER_ID;
}
