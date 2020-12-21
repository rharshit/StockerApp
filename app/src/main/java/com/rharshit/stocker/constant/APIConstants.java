package com.rharshit.stocker.constant;

import com.rharshit.stocker.BuildConfig;

public abstract class APIConstants {

    private APIConstants() {
        throw new IllegalStateException();
    }

    /**
     * Generic
     */
    public static final String ACCESS_KEY = "access_key";
    public static final String LIMIT = "limit";

    /**
     * Base URLs
     */
    public static final String BASE_URL_CHUCK = "https://api.chucknorris.io/";
    public static final String BASE_URL_MARKETSTACK = "http://api.marketstack.com/";

    /**
     * Endpoints
     */

    public static final String GET_CHUCK_NORRIS_JOKES = "/jokes/random";

    public static final String GET_MARKETSTACK_EXCHANGES = "/v1/exchanges";

    /**
     * Keys
     */
    public static final String MARKETSTACK_API_KEY = BuildConfig.MARKETSTACK_API_KEY;
}
