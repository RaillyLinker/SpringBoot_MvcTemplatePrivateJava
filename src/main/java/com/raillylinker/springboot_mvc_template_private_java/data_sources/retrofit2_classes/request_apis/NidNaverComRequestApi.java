package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NidNaverComRequestApi {

    // [Naver Oauth2 AccessToken 요청]
    @GET("/oauth2.0/token")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<GetOAuth2Dot0TokenRequestOutputVO> getOAuth2Dot0Token(
            // 무조건 "authorization_code" 입력
            @Query("grant_type")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String grantType,
            // OAuth2 ClientId
            @Query("client_id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String clientId,
            // OAuth2 clientSecret
            @Query("client_secret")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String clientSecret,
            // OAuth2 로그인할때 사용한 Redirect Uri
            @Query("redirect_uri")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String redirectUri,
            // OAuth2 로그인으로 발급받은 코드
            @Query("code")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String code,
            // OAuth2 로그인할때 사용한 state
            @Query("state")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String state
    );

    record GetOAuth2Dot0TokenRequestOutputVO(
            @SerializedName("access_token") @Expose
            String accessToken,
            @SerializedName("refresh_token") @Expose
            String refreshToken,
            @SerializedName("token_type") @Expose
            String tokenType,
            @SerializedName("expires_in") @Expose
            Long expiresIn
    ) {
    }
}
