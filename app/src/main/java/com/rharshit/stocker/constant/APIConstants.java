package com.rharshit.stocker.constant;

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
}
