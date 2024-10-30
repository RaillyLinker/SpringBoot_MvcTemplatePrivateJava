package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

// (한 주소에 대한 API 요청명세)
// 사용법은 아래 기본 사용 샘플을 참고하여 추상함수를 작성하여 사용
public interface WwwGoogleapisComRequestApi {
    // [Google Oauth2 AccessToken 요청]
    @GET("/oauth2/v1/userinfo")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<GetOauth2V1UserInfoOutputVO> getOauth2V1UserInfo(
            @Header("Authorization")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );

    record GetOauth2V1UserInfoOutputVO(
            @SerializedName("id") @Expose
            String id,
            @SerializedName("email") @Expose
            String email,
            @SerializedName("verified_email") @Expose
            Boolean verifiedEmail,
            @SerializedName("name") @Expose
            String name,
            @SerializedName("given_name") @Expose
            String givenName,
            @SerializedName("family_name") @Expose
            String familyName,
            @SerializedName("picture") @Expose
            String picture,
            @SerializedName("locale") @Expose
            String locale
    ) {
    }
}
