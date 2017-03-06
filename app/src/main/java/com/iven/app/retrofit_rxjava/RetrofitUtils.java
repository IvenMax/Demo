package com.iven.app.retrofit_rxjava;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Iven
 * @date 2017/3/3 9:54
 * @Description
 */

public class RetrofitUtils {
    private static final String TAG = "zpy_RetrofitUtils";
    private static final String BASE_URL = "http://103.235.228.69:2020";
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit retrofit;
    private RetrofitUtils() {
    }

    public static Retrofit getInstance() {
        if (null == retrofit) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//设置超时时间
            httpClientBuilder.addInterceptor(LoggingInterceptor);//日志拦截器

            return new Retrofit.Builder().client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    ;
        } else {
            return retrofit;
        }
    }

    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.e(TAG, "intercept: 69" + "行 = " + String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Log.e(TAG, "Sending request ," + request.url());
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.e(TAG, "Received response," + response.request());
            Log.e(TAG, (String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers())));
            return response;
        }
    };
}
