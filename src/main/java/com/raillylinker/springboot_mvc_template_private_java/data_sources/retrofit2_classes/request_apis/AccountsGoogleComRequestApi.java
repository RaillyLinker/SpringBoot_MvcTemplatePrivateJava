package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// (한 주소에 대한 API 요청명세)
// 사용법은 아래 기본 사용 샘플을 참고하여 추상함수를 작성하여 사용
public interface AccountsGoogleComRequestApi {
    // [Google Oauth2 AccessToken 요청]
    @POST("/o/oauth2/token")
    @FormUrlEncoded
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostOOauth2TokenOutputVO> postOOauth2Token(
            // OAuth2 로그인으로 발급받은 코드
            @Field("code")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String code,
            // OAuth2 ClientId
            @Field("client_id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String clientId,
            // OAuth2 clientSecret
            @Field("client_secret")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String clientSecret,
            // 무조건 "authorization_code" 입력
            @Field("grant_type")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String grantType,
            // OAuth2 로그인할때 사용한 Redirect Uri
            @Field("redirect_uri")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String redirectUri
    );

    // Record 클래스를 사용하여 데이터 모델 정의
    record PostOOauth2TokenOutputVO(
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