package com.library.background;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.library.utils.ConnectionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jorgehdezvilla on 29/08/17.
 * FFM
 */

@SuppressWarnings({"all"})
public abstract class BaseWSManager<D extends BaseDefinition> {

    private Context mContext;
    private WSCallback mWSCallback;
    private Call<ResponseBody> mBaseResponseCall;
    private static final List<String> errorRegisters = new ArrayList<>();

    public BaseWSManager settings(Context context) {
        mContext = context;
        return this;
    }

    public BaseWSManager settings(WSCallback WSCallback) {
        mContext = (Context) WSCallback;
        mWSCallback = WSCallback;
        return this;
    }

    public BaseWSManager settings(Context context, WSCallback WSCallback) {
        mContext = context;
        mWSCallback = WSCallback;
        return this;
    }

    protected abstract D getDefinition();

    protected abstract String getJsonDebug(String requestUrl);

    protected abstract boolean getErrorDebugEnabled();

    protected abstract boolean getDebugEnabled();

    protected abstract long getRequestTime();

    public <R extends WSBaseResponseInterface> R requestWsSync(Class<R> tClass, String webServiceKey, Call<ResponseBody> call) {
        if (getDebugEnabled()) {
            Gson gson = new Gson();
            return gson.fromJson(getJsonDebug(webServiceKey), tClass);
        }
        try {
            mBaseResponseCall = call;
            if (mBaseResponseCall != null && mBaseResponseCall.isExecuted())
                mBaseResponseCall.cancel();
            Response<ResponseBody> bodyResponse = mBaseResponseCall.execute();
            if (bodyResponse.isSuccessful()) {
                String json = bodyResponse.body().string();
                Gson gson = new Gson();
                return gson.fromJson(json, tClass);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <R extends WSBaseResponseInterface> BaseWSManager requestWs(final Class<R> tClass, final String webServiceKey, Call<ResponseBody> call) {
        if (getDebugEnabled()) {
            mWSCallback.onRequestWS(webServiceKey);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    R response;
                    response = gson.fromJson(getJsonDebug(webServiceKey), tClass);
                    if (getErrorDebugEnabled()) {
                        if (errorRegisters.contains(webServiceKey)) {
                            mWSCallback.onSuccessLoadResponse(webServiceKey, response);
                        } else {
                            errorRegisters.add(webServiceKey);
                            mWSCallback.onErrorLoadResponse(webServiceKey, "");
                        }
                    } else {
                        mWSCallback.onSuccessLoadResponse(webServiceKey, response);
                    }
                }
            }, getRequestTime());
            return this;
        }
        if (ConnectionUtils.isConnected(mContext)) {
            mWSCallback.onRequestWS(webServiceKey);
            mBaseResponseCall = call;

            if (mBaseResponseCall != null && mBaseResponseCall.isExecuted())
                mBaseResponseCall.cancel();
            mBaseResponseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            if (response.body() != null) {
                                String json = response.body().string();
                                Gson gson = new Gson();
                                mWSCallback.onSuccessLoadResponse(webServiceKey, gson.fromJson(json, tClass));
                            } else {
                                mWSCallback.onErrorLoadResponse(webServiceKey, "Without response frow ws");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mWSCallback.onErrorLoadResponse(webServiceKey, "");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    if (!call.isCanceled())
                        mWSCallback.onErrorLoadResponse(webServiceKey, "");
                }
            });
        } else {
            mWSCallback.onErrorConnection();
        }
        return this;
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public void onDestroy() {
        if (mBaseResponseCall != null && mBaseResponseCall.isExecuted()) mBaseResponseCall.cancel();
    }

}
