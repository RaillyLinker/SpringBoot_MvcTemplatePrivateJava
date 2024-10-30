package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KauthKakaoComRequestApi {
    // [KakaoTalk Oauth2 AccessToken 요청]
    @POST("/oauth/token")
    @FormUrlEncoded
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostOOauthTokenOutputVO> postOOauthToken(
            @Field("grant_type")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String grantType, // 무조건 "authorization_code" 입력
            @Field("client_id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String clientId, // OAuth2 ClientId
            @Field("client_secret")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String clientSecret, // OAuth2 clientSecret
            @Field("redirect_uri")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String redirectUri, // OAuth2 로그인할때 사용한 Redirect Uri
            @Field("code")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String code // OAuth2 로그인으로 발급받은 코드
    );

    record PostOOauthTokenOutputVO(
            @SerializedName("access_token") @Expose
            String accessToken,
            @SerializedName("expires_in") @Expose
            Long expiresIn,
            @SerializedName("scope") @Expose
            String scope,
            @SerializedName("token_type") @Expose
            String tokenType
    ) {
    }
}
