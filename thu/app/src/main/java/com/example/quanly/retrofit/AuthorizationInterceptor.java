package com.example.quanly.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    private String authoToken;

    public AuthorizationInterceptor(String authoToken) {
        this.authoToken = authoToken;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request orinal = chain.request();
        Request.Builder builder = orinal.newBuilder()
                .header("Authorization", "Bearer " + authoToken)
                .method(orinal.method(), orinal.body());
        Request request = builder.build();
        return chain.proceed(request);
    }
}
