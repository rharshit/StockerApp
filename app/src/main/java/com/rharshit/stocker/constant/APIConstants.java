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
    public static final String BASE_URL_MARKETSTACK = "https://api.marketstack.com/v1";

    /**
     * endpoints
     */

    public static final String GET_CHUCK_NORRIS_JOKES = "/jokes/random";

    /**
     * Kays
     */
    public static final String MARKETSTACK_API_KEY = BuildConfig.MARKETSTACK_API_KEY;
}
