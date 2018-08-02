package com.library.background;

/**
 * Created by jorgehdezvilla on 29/08/17.
 * FFM
 */

public interface WSCallback {

    void onRequestWS(String requestUrl);

    void onSuccessLoadResponse(String requestUrl, WSBaseResponseInterface baseResponse);

    void onErrorLoadResponse(String requestUrl, String messageError);

    void onErrorConnection();

}
