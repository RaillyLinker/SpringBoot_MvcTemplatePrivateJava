package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import java.util.List;

// Apple IdToken 해독을 위한 공개키 가져오기
public interface AppleIdAppleComRequestApi {
    @GET("auth/keys")
    @Headers("Content-Type: application/json")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<@Valid @NotNull GetAuthKeysOutputVo> getAuthKeys();

    // record 클래스를 사용하여 데이터 모델 정의
    record GetAuthKeysOutputVo(
            @SerializedName("keys") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<Key> keys
    ) {
        // Key record 정의
        record Key(
                @SerializedName("kty") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String kty,
                @SerializedName("kid") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String kid,
                @SerializedName("use") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String use,
                @SerializedName("alg") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String alg,
                @SerializedName("n") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String n,
                @SerializedName("e") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String e
        ) {
        }
    }
}
