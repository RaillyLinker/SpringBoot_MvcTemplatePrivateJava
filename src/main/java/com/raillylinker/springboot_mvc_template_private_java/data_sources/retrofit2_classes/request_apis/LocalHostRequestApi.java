package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface LocalHostRequestApi {
    // [기본 요청 테스트 API]
    // 이 API 를 요청하면 현재 실행중인 프로필 이름을 반환합니다.
    @GET("/service1/tk/v1/request-test")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<String> getService1TkV1RequestTest();

    // [요청 Redirect 테스트 API]
    // 이 API 를 요청하면 /service1/tk/v1/request-test 로 Redirect 됩니다.
    @GET("/service1/tk/v1/request-test/redirect-to-blank")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<String> getService1TkV1RequestTestRedirectToBlank();

    // [요청 Forward 테스트 API]
    // 이 API 를 요청하면 /service1/tk/v1/request-test 로 Forward 됩니다.
    @GET("/service1/tk/v1/request-test/forward-to-blank")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<String> getService1TkV1RequestTestForwardToBlank();

    // [Get 요청(Query Parameter) 테스트 API]
    // Query Parameter 를 받는 Get 메소드 요청 테스트
    @GET("/service1/tk/v1/request-test/get-request")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<GetService1TkV1RequestTestGetRequestOutputVO> getService1TkV1RequestTestGetRequest(
            @Query("queryParamString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,
            @Query("queryParamStringNullable")
            String queryParamStringNullable,
            @Query("queryParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,
            @Query("queryParamIntNullable")
            Integer queryParamIntNullable,
            @Query("queryParamDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,
            @Query("queryParamDoubleNullable")
            Double queryParamDoubleNullable,
            @Query("queryParamBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,
            @Query("queryParamBooleanNullable")
            Boolean queryParamBooleanNullable,
            @Query("queryParamStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,
            @Query("queryParamStringListNullable")
            List<@Valid @NotNull String> queryParamStringListNullable
    );

    record GetService1TkV1RequestTestGetRequestOutputVO(
            @SerializedName("queryParamString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,
            @SerializedName("queryParamStringNullable") @Expose
            String queryParamStringNullable,
            @SerializedName("queryParamInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,
            @SerializedName("queryParamIntNullable") @Expose
            Integer queryParamIntNullable,
            @SerializedName("queryParamDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,
            @SerializedName("queryParamDoubleNullable") @Expose
            Double queryParamDoubleNullable,
            @SerializedName("queryParamBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,
            @SerializedName("queryParamBooleanNullable") @Expose
            Boolean queryParamBooleanNullable,
            @SerializedName("queryParamStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,
            @SerializedName("queryParamStringListNullable") @Expose
            List<@Valid @NotNull String> queryParamStringListNullable
    ) {
    }

    // [Get 요청(Path Parameter) 테스트 API]
    // Path Parameter 를 받는 Get 메소드 요청 테스트
    @GET("/service1/tk/v1/request-test/get-request/{pathParamInt}")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<GetService1TkV1RequestTestGetRequestPathParamIntOutputVO> getService1TkV1RequestTestGetRequestPathParamInt(
            @Path("pathParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    );

    record GetService1TkV1RequestTestGetRequestPathParamIntOutputVO(
            @SerializedName("pathParamInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    ) {
    }

    // [Post 요청(Application-Json) 테스트 API]
    // application-json 형태의 Request Body 를 받는 Post 메소드 요청 테스트
    @POST("/service1/tk/v1/request-test/post-request-application-json")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostService1TkV1RequestTestPostRequestApplicationJsonOutputVO> postService1TkV1RequestTestPostRequestApplicationJson(
            @Body
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostService1TkV1RequestTestPostRequestApplicationJsonInputVO inputVo
    );

    record PostService1TkV1RequestTestPostRequestApplicationJsonInputVO(
            @SerializedName("requestBodyString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBodyString,
            @SerializedName("requestBodyStringNullable") @Expose
            String requestBodyStringNullable,
            @SerializedName("requestBodyInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestBodyInt,
            @SerializedName("requestBodyIntNullable") @Expose
            Integer requestBodyIntNullable,
            @SerializedName("requestBodyDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestBodyDouble,
            @SerializedName("requestBodyDoubleNullable") @Expose
            Double requestBodyDoubleNullable,
            @SerializedName("requestBodyBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestBodyBoolean,
            @SerializedName("requestBodyBooleanNullable") @Expose
            Boolean requestBodyBooleanNullable,
            @SerializedName("requestBodyStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList,
            @SerializedName("requestBodyStringListNullable") @Expose
            List<@Valid @NotNull String> requestBodyStringListNullable
    ) {
    }

    record PostService1TkV1RequestTestPostRequestApplicationJsonOutputVO(
            @SerializedName("requestBodyString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBodyString,
            @SerializedName("requestBodyStringNullable") @Expose
            String requestBodyStringNullable,
            @SerializedName("requestBodyInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestBodyInt,
            @SerializedName("requestBodyIntNullable") @Expose
            Integer requestBodyIntNullable,
            @SerializedName("requestBodyDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestBodyDouble,
            @SerializedName("requestBodyDoubleNullable") @Expose
            Double requestBodyDoubleNullable,
            @SerializedName("requestBodyBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestBodyBoolean,
            @SerializedName("requestBodyBooleanNullable") @Expose
            Boolean requestBodyBooleanNullable,
            @SerializedName("requestBodyStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList,
            @SerializedName("requestBodyStringListNullable") @Expose
            List<@Valid @NotNull String> requestBodyStringListNullable
    ) {
    }

    // [Post 요청(x-www-form-urlencoded) 테스트 API]
    // x-www-form-urlencoded 형태의 Request Body 를 받는 Post 메소드 요청 테스트
    @POST("service1/tk/v1/request-test/post-request-x-www-form-urlencoded")
    @FormUrlEncoded
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostService1TkV1RequestTestPostRequestXWwwFormUrlencodedOutputVO> postService1TkV1RequestTestPostRequestXWwwFormUrlencoded(
            @Field("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @Field("requestFormStringNullable")
            String requestFormStringNullable,
            @Field("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @Field("requestFormIntNullable")
            Integer requestFormIntNullable,
            @Field("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @Field("requestFormDoubleNullable")
            Double requestFormDoubleNullable,
            @Field("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @Field("requestFormBooleanNullable")
            Boolean requestFormBooleanNullable,
            @Field("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @Field("requestFormStringListNullable")
            List<@Valid @NotNull String> requestFormStringListNullable
    );

    record PostService1TkV1RequestTestPostRequestXWwwFormUrlencodedOutputVO(
            @SerializedName("requestFormString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @SerializedName("requestFormStringNullable") @Expose
            String requestFormStringNullable,
            @SerializedName("requestFormInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @SerializedName("requestFormIntNullable") @Expose
            Integer requestFormIntNullable,
            @SerializedName("requestFormDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @SerializedName("requestFormDoubleNullable") @Expose
            Double requestFormDoubleNullable,
            @SerializedName("requestFormBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @SerializedName("requestFormBooleanNullable") @Expose
            Boolean requestFormBooleanNullable,
            @SerializedName("requestFormStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @SerializedName("requestFormStringListNullable") @Expose
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }

    // [Post 요청(multipart/form-data) 테스트 API]
    // multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트(Multipart File List)
    // MultipartFile 파라미터가 null 이 아니라면 저장
    @POST("/service1/tk/v1/request-test/post-request-multipart-form-data")
    @Multipart
    Call<PostService1TkV1RequestTestPostRequestMultipartFormDataOutputVO> postService1TkV1RequestTestPostRequestMultipartFormData(
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormString,
            @Part
            MultipartBody.Part requestFormStringNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormInt,
            @Part
            MultipartBody.Part requestFormIntNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormDouble,
            @Part
            MultipartBody.Part requestFormDoubleNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormBoolean,
            @Part
            MultipartBody.Part requestFormBooleanNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<MultipartBody.Part> requestFormStringList,
            @Part
            List<MultipartBody.Part> requestFormStringListNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part multipartFile,
            @Part
            MultipartBody.Part multipartFileNullable
    );

    record PostService1TkV1RequestTestPostRequestMultipartFormDataOutputVO(
            @SerializedName("requestFormString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @SerializedName("requestFormStringNullable") @Expose
            String requestFormStringNullable,
            @SerializedName("requestFormInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @SerializedName("requestFormIntNullable") @Expose
            Integer requestFormIntNullable,
            @SerializedName("requestFormDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @SerializedName("requestFormDoubleNullable") @Expose
            Double requestFormDoubleNullable,
            @SerializedName("requestFormBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @SerializedName("requestFormBooleanNullable") @Expose
            Boolean requestFormBooleanNullable,
            @SerializedName("requestFormStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @SerializedName("requestFormStringListNullable") @Expose
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }

    // [Post 요청(multipart/form-data list) 테스트 API]
    // multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트(Multipart File List)
    // 파일 리스트가 null 이 아니라면 저장
    @POST("/service1/tk/v1/request-test/post-request-multipart-form-data2")
    @Multipart
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostService1TkV1RequestTestPostRequestMultipartFormData2VO> postService1TkV1RequestTestPostRequestMultipartFormData2(
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormString,
            @Part
            MultipartBody.Part requestFormStringNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormInt,
            @Part
            MultipartBody.Part requestFormIntNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormDouble,
            @Part
            MultipartBody.Part requestFormDoubleNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part requestFormBoolean,
            @Part
            MultipartBody.Part requestFormBooleanNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<MultipartBody.Part> requestFormStringList,
            @Part
            List<MultipartBody.Part> requestFormStringListNullable,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<MultipartBody.Part> multipartFileList,
            @Part
            List<MultipartBody.Part> multipartFileNullableList
    );

    record PostService1TkV1RequestTestPostRequestMultipartFormData2VO(
            @SerializedName("requestFormString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @SerializedName("requestFormStringNullable") @Expose
            String requestFormStringNullable,
            @SerializedName("requestFormInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @SerializedName("requestFormIntNullable") @Expose
            Integer requestFormIntNullable,
            @SerializedName("requestFormDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @SerializedName("requestFormDoubleNullable") @Expose
            Double requestFormDoubleNullable,
            @SerializedName("requestFormBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @SerializedName("requestFormBooleanNullable") @Expose
            Boolean requestFormBooleanNullable,
            @SerializedName("requestFormStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @SerializedName("requestFormStringListNullable") @Expose
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }

    // [Post 요청(multipart/form-data list) 테스트 API]
    // multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트(Multipart File List)
    // 파일 리스트가 null 이 아니라면 저장
    @POST("/service1/tk/v1/request-test/post-request-multipart-form-data-json")
    @Multipart
    Call<PostService1TkV1RequestTestPostRequestMultipartFormDataJsonOutputVO> postService1TkV1RequestTestPostRequestMultipartFormDataJson(
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part jsonString,
            @Part
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartBody.Part multipartFile,
            @Part
            MultipartBody.Part multipartFileNullable
    );

    record PostService1TkV1RequestTestPostRequestMultipartFormDataJsonJsonStringVo(
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @JsonProperty("requestFormStringNullable")
            String requestFormStringNullable,
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @JsonProperty("requestFormIntNullable")
            Integer requestFormIntNullable,
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @JsonProperty("requestFormDoubleNullable")
            Double requestFormDoubleNullable,
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @JsonProperty("requestFormBooleanNullable")
            Boolean requestFormBooleanNullable,
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<String> requestFormStringList,
            @JsonProperty("requestFormStringListNullable")
            List<String> requestFormStringListNullable
    ) {
    }

    record PostService1TkV1RequestTestPostRequestMultipartFormDataJsonOutputVO(
            @SerializedName("requestFormString") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @SerializedName("requestFormStringNullable") @Expose
            String requestFormStringNullable,
            @SerializedName("requestFormInt") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @SerializedName("requestFormIntNullable") @Expose
            Integer requestFormIntNullable,
            @SerializedName("requestFormDouble") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @SerializedName("requestFormDoubleNullable") @Expose
            Double requestFormDoubleNullable,
            @SerializedName("requestFormBoolean") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @SerializedName("requestFormBooleanNullable") @Expose
            Boolean requestFormBooleanNullable,
            @SerializedName("requestFormStringList") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @SerializedName("requestFormStringListNullable") @Expose
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }

    // [인위적 에러 발생 테스트 API]
    // 요청 받으면 인위적인 서버 에러를 발생시킵니다.(Http Response Status 500)
    @POST("service1/tk/v1/request-test/generate-error")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<Void> postService1TkV1RequestTestGenerateError();

    // [결과 코드 발생 테스트 API]
    // Response Header 에 api-result-code 를 반환하는 테스트 API
    //(api-result-code)
    // 1 : errorType 을 A 로 보냈습니다.
    // 2 : errorType 을 B 로 보냈습니다.
    // 3 : errorType 을 C 로 보냈습니다.
    @POST("/service1/tk/v1/request-test/api-result-code-test")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<Void> postService1TkV1RequestTestApiResultCodeTest(
            @Query("errorType")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostService1TkV1RequestTestApiResultCodeTestErrorTypeEnum errorType
    );

    enum PostService1TkV1RequestTestApiResultCodeTestErrorTypeEnum {
        A,
        B,
        C
    }

    // [인위적 타임아웃 에러 발생 테스트]
    // 타임아웃 에러를 발생시키기 위해 임의로 응답 시간을 지연시킵니다.
    @POST("/service1/tk/v1/request-test/time-delay-test")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<Void> postService1TkV1RequestTestGenerateTimeOutError(
            @Query("delayTimeSec")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long delayTimeSec
    );

    // [text/string 반환 샘플]
    // text/string 형식의 Response Body 를 반환합니다.
    @GET("/service1/tk/v1/request-test/return-text-string")
    @Headers("Content-Type: text/string")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<String> getService1TkV1RequestTestReturnTextString();

    // [text/html 반환 샘플]
    // text/html 형식의 Response Body 를 반환합니다.
    @GET("/service1/tk/v1/request-test/return-text-html")
    @Headers("Content-Type: text/html")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<String> getService1TkV1RequestTestReturnTextHtml();

    // [비동기 처리 결과 반환 샘플]
    // API 호출시 함수 내에서 별도 스레드로 작업을 수행하고,
    // 비동기 작업 완료 후 그 처리 결과가 반환됨
    @GET("/service1/tk/v1/request-test/async-result")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<GetService1TkV1RequestTestAsyncResultOutputVO> getService1TkV1RequestTestAsyncResult();

    record GetService1TkV1RequestTestAsyncResultOutputVO(
            @SerializedName("resultMessage") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String resultMessage
    ) {
    }
}
