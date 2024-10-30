package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes;

import com.google.gson.Gson;
import com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RepositoryNetworkRetrofit2 {
    // <멤버 변수 공간>
    // (Network Request Api 객체들)
    // !!!요청을 보낼 서버 위치 경로별 RequestApi 객체를 생성하기!!!

    // (로컬 테스트용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final LocalHostRequestApi localHostRequestApi = getRetrofitClient(
            "http://localhost:8080",
            7000L,
            7000L,
            7000L,
            false)
            .create(LocalHostRequestApi.class
            );

    // (Naver SMS 발송용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final SensApigwNtrussComRequestApi sensApigwNtrussComRequestApi = getRetrofitClient(
            "https://sens.apigw.ntruss.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(SensApigwNtrussComRequestApi.class
            );

    // (Google AccessToken 발급용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final AccountsGoogleComRequestApi accountsGoogleComRequestApi = getRetrofitClient(
            "https://accounts.google.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(AccountsGoogleComRequestApi.class
            );

    // (Naver AccessToken 발급용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final AccountsGoogleComRequestApi nidNaverComRequestApi = getRetrofitClient(
            "https://nid.naver.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(AccountsGoogleComRequestApi.class
            );

    // (KakaoTalk AccessToken 발급용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final KauthKakaoComRequestApi kauthKakaoComRequestApi = getRetrofitClient(
            "https://kauth.kakao.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(KauthKakaoComRequestApi.class
            );

    // (Google AccessToken 으로 Profile 조회용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final WwwGoogleapisComRequestApi wwwGoogleapisComRequestApi = getRetrofitClient(
            "https://www.googleapis.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(WwwGoogleapisComRequestApi.class
            );

    // (Naver AccessToken 으로 Profile 조회용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final OpenapiNaverComRequestApi openapiNaverComRequestApi = getRetrofitClient(
            "https://openapi.naver.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(OpenapiNaverComRequestApi.class
            );

    // (KakaoTalk AccessToken 으로 Profile 조회용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final KapiKakaoComRequestApi kapiKakaoComRequestApi = getRetrofitClient(
            "https://kapi.kakao.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(KapiKakaoComRequestApi.class
            );

    // (Apple OAuth2 IdToken 공개키 요청용)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public final AppleIdAppleComRequestApi appleIdAppleComRequestApi = getRetrofitClient(
            "https://appleid.apple.com",
            7000L,
            7000L,
            7000L,
            false)
            .create(AppleIdAppleComRequestApi.class
            );


    // ---------------------------------------------------------------------------------------------
    // <Static 공간>
    // (싱글톤 설정)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final Semaphore singletonSemaphore = new Semaphore(1);
    private static RepositoryNetworkRetrofit2 instance;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static RepositoryNetworkRetrofit2 getInstance() {
        try {
            singletonSemaphore.acquire();
            if (instance == null) {
                instance = new RepositoryNetworkRetrofit2();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            singletonSemaphore.release();
        }
        return instance;
    }


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>


    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>
    // (baseUrl 에 접속하는 레트로핏 객체를 생성 반환하는 함수)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private Retrofit getRetrofitClient(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String baseUrl,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long connectTimeoutMillis,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long readTimeoutMillis,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long writeTimeoutMillis,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean retryOnConnectionFailure
    ) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor((Interceptor.Chain chain) -> {
            Request originRequest = chain.request();
            HttpUrl addedUrl = originRequest.url().newBuilder().build();
            Request finalRequest = originRequest.newBuilder()
                    .url(addedUrl)
                    .method(originRequest.method(), originRequest.body())
                    .build();
            return chain.proceed(finalRequest);
        });

        okHttpClientBuilder.connectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(writeTimeoutMillis, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(retryOnConnectionFailure);

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Logger logger = LoggerFactory.getLogger(RepositoryNetworkRetrofit2.class);
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> logger.debug("[Retrofit2 Log] : {}", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClientBuilder.build())
                .build();
    }
}
