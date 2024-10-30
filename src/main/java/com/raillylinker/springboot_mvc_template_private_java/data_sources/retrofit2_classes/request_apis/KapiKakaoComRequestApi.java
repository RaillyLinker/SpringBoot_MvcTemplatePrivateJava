package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface KapiKakaoComRequestApi {
    // [KakaoTalk Oauth2 AccessToken 요청]
    @GET("v2/user/me")
    @Headers("Connection: close")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<@Valid @NotNull GetV2UserMeOutputVO> getV2UserMe(
            @Header("Authorization")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );

    // (kakao profile 정보 요청 API 응답 객체)
    // https://kapi.kakao.com/v2/user/me
    record GetV2UserMeOutputVO(
            @SerializedName("id") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long id, // 회원번호
            @SerializedName("connected_at") @Expose
            String connectedAt, // 연결 완료된 시각
            @SerializedName("kakao_account") @Expose
            KakaoAccountVo kakaoAccount // 카카오계정 정보
    ) {
        record KakaoAccountVo(
                @SerializedName("profile_needs_agreement") @Expose
                Boolean profileNeedsAgreement,
                @SerializedName("profile_nickname_needs_agreement") @Expose
                Boolean profileNicknameNeedsAgreement,
                @SerializedName("profile_image_needs_agreement") @Expose
                Boolean profileImageNeedsAgreement,
                @SerializedName("profile") @Expose
                ProfileVo profile,
                @SerializedName("name_needs_agreement") @Expose
                Boolean nameNeedsAgreement,
                @SerializedName("name") @Expose
                String name,
                @SerializedName("email_needs_agreement") @Expose
                Boolean emailNeedsAgreement,
                @SerializedName("is_email_valid") @Expose
                Boolean isEmailValid,
                @SerializedName("is_email_verified") @Expose
                Boolean isEmailVerified,
                @SerializedName("email") @Expose
                String email,
                @SerializedName("age_range_needs_agreement") @Expose
                Boolean ageRangeNeedsAgreement,
                @SerializedName("age_range") @Expose
                String ageRange,
                @SerializedName("birthyear_needs_agreement") @Expose
                Boolean birthyearNeedsAgreement,
                @SerializedName("birthyear") @Expose
                String birthyear,
                @SerializedName("birthday_type") @Expose
                String birthdayType,
                @SerializedName("gender_needs_agreement") @Expose
                Boolean genderNeedsAgreement,
                @SerializedName("gender") @Expose
                String gender,
                @SerializedName("phone_number_needs_agreement") @Expose
                Boolean phoneNumberNeedsAgreement,
                @SerializedName("phone_number") @Expose
                String phoneNumber,
                @SerializedName("ci_needs_agreement") @Expose
                Boolean ciNeedsAgreement,
                @SerializedName("ci") @Expose
                String ci,
                @SerializedName("ci_authenticated_at") @Expose
                String ciAuthenticatedAt
        ) {
            record ProfileVo(
                    @SerializedName("nickname") @Expose
                    String nickname,
                    @SerializedName("thumbnail_image_url") @Expose
                    String thumbnailImageUrl,
                    @SerializedName("profile_image_url") @Expose
                    String profileImageUrl,
                    @SerializedName("is_default_image") @Expose
                    String isDefaultImage
            ) {
            }
        }
    }
}
