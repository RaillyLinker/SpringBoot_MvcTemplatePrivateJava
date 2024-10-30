package com.raillylinker.springboot_mvc_template_private_java.data_sources.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SensApigwNtrussComRequestApi {
    // (Naver SMS 발송)
    // https://api.ncloud-docs.com/docs/ai-application-service-sens-smsv2
    @POST("sms/v2/services/{naverSmsServiceId}/messages")
    @Headers("Content-Type: application/json")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostSmsV2ServicesNaverSmsServiceIdMessagesOutputVO> postSmsV2ServicesNaverSmsServiceIdMessages(
            // 프로젝트 등록 시 발급받은 서비스 아이디
            @Path("naverSmsServiceId")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String naverSmsServiceId,
            // 1970년 1월 1일 00:00:00 협정 세계시(UTC)부터의 경과 시간을 밀리초(Millisecond)로 나타냄
            // API Gateway 서버와 시간 차가 5분 이상 나는 경우 유효하지 않은 요청으로 간주
            @Header("x-ncp-apigw-timestamp")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpApigwTimestamp,
            // 포탈 또는 Sub Account에서 발급받은 Access Key ID
            @Header("x-ncp-iam-access-key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpIamAccessKey,
            // 위 예제의 Body를 Access Key Id와 맵핑되는 SecretKey로 암호화한 서명, HMAC 암호화 알고리즘은 HmacSHA256 사용
            @Header("x-ncp-apigw-signature-v2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpApigwSignatureV2,
            @Body
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostSmsV2ServicesNaverSmsServiceIdMessagesInputVO inputVo
    );

    record PostSmsV2ServicesNaverSmsServiceIdMessagesInputVO(
            // SMS Type, SMS, LMS, MMS (소문자 가능)
            @SerializedName("type") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String type,
            // 메시지 Type, COMM: 일반메시지 default, AD: 광고메시지
            @SerializedName("contentType") @Expose
            String contentType,
            // 국가 번호, SENS에서 제공하는 국가로의 발송만 가능, default: 82
            // https://guide.ncloud-docs.com/docs/sens-smspolicy
            @SerializedName("countryCode") @Expose
            String countryCode,
            // 발신번호, 사전 등록된 발신번호만 사용 가능
            @SerializedName("from") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String from,
            // 기본 메시지 제목, LMS, MMS에서만 사용 가능(최대 40byte)
            @SerializedName("subject") @Expose
            String subject,
            // 기본 메시지 내용, SMS: 최대 90byte, LMS, MMS: 최대 2000byte
            @SerializedName("content") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,
            // 메시지 정보, 최대 100개
            @SerializedName("messages") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull MessageVo> messages,
            // 파일 전송
            @SerializedName("files") @Expose
            List<@Valid @NotNull FileVo> files,
            // 메시지 발송 예약 일시 (yyyy-MM-dd HH:mm)
            @SerializedName("reserveTime") @Expose
            String reserveTime,
            // 예약 일시 타임존 (기본: Asia/Seoul) https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
            @SerializedName("reserveTimeZone") @Expose
            String reserveTimeZone
    ) {
        public record MessageVo(
                // 수신번호, 붙임표 ( - )를 제외한 숫자만 입력 가능
                @SerializedName("to") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String to,
                // 개별 메시지 제목, LMS, MMS에서만 사용 가능(최대 40byte)
                @SerializedName("subject") @Expose
                String subject,
                // 개별 메시지 내용, SMS: 최대 90byte, LMS, MMS: 최대 2000byte
                @SerializedName("content") @Expose
                String content
        ) {
        }

        public record FileVo(
                // 파일 아이디, MMS 에서만 사용 가능, 파일 전송 api 사용 후 받아온 fileId 를 입력
                @SerializedName("fileId") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String fileId
        ) {
        }
    }

    record PostSmsV2ServicesNaverSmsServiceIdMessagesOutputVO(
            // 요청 아이디
            @SerializedName("requestId") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestId,
            // 요청 시간 yyyy-MM-dd'T'HH:mm:ss.SSS
            @SerializedName("requestTime") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestTime,
            // 요청 상태 코드, 202: 성공, 그 외: 실패, HTTP Status 규격을 따름
            @SerializedName("statusCode") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String statusCode,
            // 요청 상태명, success: 성공, fail: 실패
            @SerializedName("statusName") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String statusName
    ) {
    }


    // (Naver SMS 파일 발송)
    // postSmsV2ServicesNaverSmsServiceIdMessages API 에서 파일을 전송하기 전에,
    // 이것을 실행하여 fileId 를 발급받아서 전송하면 됩니다.
    // https://api.ncloud-docs.com/docs/ai-application-service-sens-smsv2
    @POST("sms/v2/services/{naverSmsServiceId}/files")
    @Headers("Content-Type: application/json")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostSmsV2ServicesNaverSmsServiceIdFilesOutputVO> postSmsV2ServicesNaverSmsServiceIdFiles(
            // 프로젝트 등록 시 발급받은 서비스 아이디
            @Path("naverSmsServiceId")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String naverSmsServiceId,
            // 1970년 1월 1일 00:00:00 협정 세계시(UTC)부터의 경과 시간을 밀리초(Millisecond)로 나타냄
            // API Gateway 서버와 시간 차가 5분 이상 나는 경우 유효하지 않은 요청으로 간주
            @Header("x-ncp-apigw-timestamp")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpApigwTimestamp,
            // 포탈 또는 Sub Account에서 발급받은 Access Key ID
            @Header("x-ncp-iam-access-key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpIamAccessKey,
            // 위 예제의 Body를 Access Key Id와 맵핑되는 SecretKey로 암호화한 서명, HMAC 암호화 알고리즘은 HmacSHA256 사용
            @Header("x-ncp-apigw-signature-v2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpApigwSignatureV2,
            @Body
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostSmsV2ServicesNaverSmsServiceIdFilesInputVO inputVo
    );

    record PostSmsV2ServicesNaverSmsServiceIdFilesInputVO(
            // 파일 이름, .jpg, .jpeg 확장자를 가진 파일 이름, 최대 40자
            @SerializedName("fileName") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName,
            // 파일 바디, .jpg, .jpeg 이미지를 Base64로 인코딩한 값, 원 파일 기준 최대 300Kbyte, 해상도 최대 1500 * 1440
            @SerializedName("fileBody") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileBody
    ) {
    }

    record PostSmsV2ServicesNaverSmsServiceIdFilesOutputVO(
            // 파일 아이디, MMS 메시지 발송 시 활용
            @SerializedName("fileId") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileId,
            // 파일 업로드 시간 yyyy-MM-dd'T'HH:mm:ss.SSS
            @SerializedName("createTime") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String createTime,
            // 파일 만료 시간 yyyy-MM-dd'T'HH:mm:ss.SSS
            @SerializedName("expireTime") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String expireTime
    ) {
    }


    // (Naver Alimtalk 발송)
    // https://api.ncloud-docs.com/docs/ai-application-service-sens-alimtalkv2#%EB%A9%94%EC%8B%9C%EC%A7%80-%EB%B0%9C%EC%86%A1
    @POST("alimtalk/v2/services/{naverSmsServiceId}/messages")
    @Headers("Content-Type: application/json")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<PostAlimtalkV2ServicesNaverSmsServiceIdMessagesOutputVO> postAlimtalkV2ServicesNaverSmsServiceIdMessages(
            // 프로젝트 등록 시 발급받은 서비스 아이디
            @Path("naverSmsServiceId")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String naverSmsServiceId,
            // 1970년 1월 1일 00:00:00 협정 세계시(UTC)부터의 경과 시간을 밀리초(Millisecond)로 나타냄
            // API Gateway 서버와 시간 차가 5분 이상 나는 경우 유효하지 않은 요청으로 간주
            @Header("x-ncp-apigw-timestamp")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpApigwTimestamp,
            // 포탈 또는 Sub Account에서 발급받은 Access Key ID
            @Header("x-ncp-iam-access-key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpIamAccessKey,
            // 위 예제의 Body를 Access Key Id와 맵핑되는 SecretKey로 암호화한 서명, HMAC 암호화 알고리즘은 HmacSHA256 사용
            @Header("x-ncp-apigw-signature-v2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String xNcpApigwSignatureV2,
            @Body
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO inputVo
    );

    record PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO(
            // 카카오톡 채널명 ((구)플러스친구 아이디)
            @SerializedName("plusFriendId") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String plusFriendId,
            // 템플릿 코드
            @SerializedName("templateCode") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String templateCode,
            // 메시지 정보, 최대 100개
            @SerializedName("messages") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull MessageVo> messages,
            // 메시지 발송 예약 일시 (yyyy-MM-dd HH:mm)
            @SerializedName("reserveTime") @Expose
            String reserveTime,
            // 예약 일시 타임존 (기본: Asia/Seoul) https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
            @SerializedName("reserveTimeZone") @Expose
            String reserveTimeZone
    ) {
        public record MessageVo(
                // 수신자 국가번호, default: 82
                @SerializedName("countryCode") @Expose
                String countryCode,
                // 수신자번호
                @SerializedName("to") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String to,
                // 알림톡 강조표시 내용, 강조 표기 유형의 템플릿에서만 사용 가능
                @SerializedName("title") @Expose
                String title,
                // 알림톡 메시지 내용
                @SerializedName("content") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,
                // 알림톡 헤더 내용, 아이템 리스트 유형의 템플릿에서만 사용 가능, 16 bytes 미만 까지 입력 가능
                @SerializedName("headerContent") @Expose
                String headerContent,
                // 아이템 하이라이트, 아이템 리스트 유형의 템플릿에서만 사용 가능
                @SerializedName("itemHighlight") @Expose
                ItemHighlightVo itemHighlight,
                // 아이템 리스트, 아이템리스트 유형의 템플릿에서만 사용 가능
                @SerializedName("item") @Expose
                ItemVo item,
                // 알림톡 메시지 버튼
                @SerializedName("buttons") @Expose
                List<@Valid @NotNull ButtonVo> buttons,
                // SMS Failover 사용 여부, Failover가 설정된 카카오톡 채널에서만 사용 가능, 기본: 카카오톡 채널의 Failover 설정 여부를 따름
                @SerializedName("useSmsFailover") @Expose
                Boolean useSmsFailover,
                // Failover 설정
                @SerializedName("failoverConfig") @Expose
                FailOverConfigVo failoverConfig
        ) {
            public record ItemHighlightVo(
                    // 아이템 하이라이트 제목, 아이템 리스트 유형의 템플릿에서만 사용 가능
                    // 이미지가 없는 경우 : 최대 30자까지 입력 가능 (2줄), 1줄은 15자까지 입력 가능
                    // 이미지가 있는 경우 : 최대 21자까지 입력 가능 (2줄), 1줄은 10자까지 입력 가능, 2줄 초과 시 말줄임 처리
                    @SerializedName("title") @Expose
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String title,
                    // 아이템 하이라이트 설명, 아이템 리스트 유형의 템플릿에서만 사용 가능
                    // 이미지가 없는 경우 : 최대 19자까지 입력 가능 (1줄)
                    // 이미지가 있는 경우 : 최대 13자까지 입력 가능 (1줄), 1줄 초과 시 말줄임 처리
                    @SerializedName("description") @Expose
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String description
            ) {
            }

            public record ItemVo(
                    // 아이템 리스트, 아이템리스트 유형의 템플릿에서만 사용 가능, 최소 2개 이상, 최대 10개
                    @SerializedName("list") @Expose
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    List<@Valid @NotNull ListItemVo> list,
                    // 아이템 요약 정보, 아이템리스트 유형의 템플릿에서만 사용 가능
                    @SerializedName("summary") @Expose
                    SummaryVo summary
            ) {
                public record ListItemVo(
                        // 아이템 리스트 제목, 아이템리스트 유형의 템플릿에서만 사용 가능, 최대 6자까지 입력 가능
                        @SerializedName("title") @Expose
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        String title,
                        // 아이템 리스트 설명, 아이템리스트 유형의 템플릿에서만 사용 가능, 최대 23자까지 입력 가능
                        @SerializedName("description") @Expose
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        String description
                ) {
                }

                public record SummaryVo(
                        // 아이템 요약 제목, 아이템리스트 유형의 템플릿에서만 사용 가능, 최대 6자까지 입력 가능
                        @SerializedName("title") @Expose
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        String title,
                        // 아이템 요약 설명, 아이템리스트 유형의 템플릿에서만 사용 가능,
                        // 허용되는 문자: 통화기호(유니코드 통화기호, 元, 円, 원), 통화코드 (ISO 4217), 숫자, 콤마, 소수점, 공백
                        // 소수점 2자리까지 허용, 최대 23자까지 입력 가능
                        @SerializedName("description") @Expose
                        @Valid @NotNull @org.jetbrains.annotations.NotNull
                        String description
                ) {
                }
            }

            record ButtonVo(
                /*
                    type        name        필수 항목
                    DS          배송 조회
                    WL          웹 링크      linkMobile, linkPc (http:// 또는 https://로 시작하는 URL)
                    AL          앱 링크      schemeIos, schemeAndroid
                    BK          봇 키워드
                    MD          메시지 전달
                    AC          채널 추가    버튼 명은 채널 추가 로 고정
                 */
                    // 버튼 Type
                    @SerializedName("type") @Expose
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String type,
                    // 버튼명
                    @SerializedName("name") @Expose
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String name,
                    @SerializedName("linkMobile") @Expose
                    String linkMobile,
                    @SerializedName("linkPc") @Expose
                    String linkPc,
                    @SerializedName("schemeIos") @Expose
                    String schemeIos,
                    @SerializedName("schemeAndroid") @Expose
                    String schemeAndroid
            ) {
            }

            public record FailOverConfigVo(
                    // Failover SMS 메시지 Type, SMS 또는 LMS, 기본: content 길이에 따라 자동 적용(90 bytes 이하 SMS, 초과 LMS)
                    @SerializedName("type") @Expose
                    String type,
                    // Failover SMS 발신번호, 기본: Failover 설정 시 선택한 발신번호, 승인되지 않은 발신번호 사용시 Failover 동작 안함
                    @SerializedName("from") @Expose
                    String from,
                    // Failover SMS 제목, LMS type으로 동작할 때 사용, 기본: 카카오톡 채널명
                    @SerializedName("subject") @Expose
                    String subject,
                    // Failover SMS 내용, 기본: 알림톡 메시지 내용 (버튼 제외)
                    @SerializedName("content") @Expose
                    String content
            ) {
            }
        }
    }

    record PostAlimtalkV2ServicesNaverSmsServiceIdMessagesOutputVO(
            // 발송 요청 아이디
            @SerializedName("requestId") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestId,
            // 발송 요청 시간, yyyy-MM-dd'T'HH:mm:ss.SSS
            @SerializedName("requestTime") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestTime,
            // 요청 상태 코드, 성공: 202, 실패: 그 외, HTTP Status 규격을 따름
            @SerializedName("statusCode") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String statusCode,
            // 요청 상태명, 성공: success, 처리 중: processing, 예약 중: reserved, 실패: fail
            @SerializedName("statusName") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String statusName,
            // 메시지
            @SerializedName("messages") @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull MessageVo> messages
    ) {
        public record MessageVo(
                // 메시지 아이디
                @SerializedName("messageId") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String messageId,
                // 수신자 국가번호, default: 82
                @SerializedName("countryCode") @Expose
                String countryCode,
                // 수신자 번호
                @SerializedName("to") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String to,
                // 알림톡 메시지 내용
                @SerializedName("content") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,
                // 발송요청 상태 코드, 성공: A000, 실패: 그 외 코드(Desc 항목에 실패 사유가 명시)
                @SerializedName("requestStatusCode") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestStatusCode,
                // 발송 요청 상태명, 성공: success, 실패: fail
                @SerializedName("requestStatusName") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestStatusName,
                // 발송 요청 상태 내용
                @SerializedName("requestStatusDesc") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestStatusDesc,
                // SMS Failover 사용 여부
                @SerializedName("useSmsFailover") @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean useSmsFailover
        ) {
        }
    }
}
