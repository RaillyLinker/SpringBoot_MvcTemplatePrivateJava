package com.raillylinker.springboot_mvc_template_private_java.filters;

import com.raillylinker.springboot_mvc_template_private_java.abstract_classes.BasicRedisMap;
import com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main.Redis1_Map_RuntimeConfigIpList;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {
    public LoggingFilter(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList
    ) {
        this.redis1RuntimeConfigIpList = redis1RuntimeConfigIpList;
    }

    // (Redis Repository)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList;

    // <멤버 변수 공간>
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final org.slf4j.Logger classLogger = LoggerFactory.getLogger(this.getClass());

    // 로깅 body 에 표시할 데이터 타입
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final List<MediaType> visibleTypeList = List.of(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml")
    );

    // ---------------------------------------------------------------------------------------------
    // <상속 메소드 공간>
    @Override
    protected void doFilterInternal(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletRequest request,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse response,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            FilterChain filterChain)
            throws ServletException, java.io.IOException {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        LocalDateTime requestTime = LocalDateTime.now();

        // 요청자 Ip (ex : 127.0.0.1)
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String clientAddressIp = request.getRemoteAddr();

        BasicRedisMap.RedisMapDataVo<Redis1_Map_RuntimeConfigIpList.ValueVo> loggingDenyIpInfo = null;
        try {
            loggingDenyIpInfo = redis1RuntimeConfigIpList.findKeyValue(Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name());
        } catch (Exception e) {
            classLogger.error("An error occurred: ", e); // 에러 레벨로 로깅
        }

        boolean loggingDeny = false;
        if (loggingDenyIpInfo != null) {
            for (Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo loggingDenyIp : loggingDenyIpInfo.value().ipInfoList) {
                if (loggingDenyIp.ip().equals(clientAddressIp)) {
                    loggingDeny = true;
                    break;
                }
            }
        }

        if (loggingDeny) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ContentCachingRequestWrapper httpServletRequest = request instanceof ContentCachingRequestWrapper
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ContentCachingResponseWrapper httpServletResponse = response instanceof ContentCachingResponseWrapper
                ? (ContentCachingResponseWrapper) response
                : new ContentCachingResponseWrapper(response);

        boolean isError = false;
        try {
            if ("text/event-stream".equals(httpServletRequest.getHeader("accept"))) {
                // httpServletResponse 를 넣어야 response Body 출력이 제대로 되지만 text/event-stream 연결에 에러가 발생함
                filterChain.doFilter(httpServletRequest, response);
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        } catch (Exception e) {
            if (!(e instanceof ServletException && e.getCause() instanceof AccessDeniedException)) {
                isError = true;
            }
            throw e;
        } finally {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryString = httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "";
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String endpoint = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI() + queryString;

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<String, String> requestHeaders = new HashMap<>();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String headerName = headerNames.nextElement();
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String headerValue = httpServletRequest.getHeader(headerName);
                requestHeaders.put(headerName, headerValue);
            }

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] requestContentByteArray = httpServletRequest.getContentAsByteArray();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBody = requestContentByteArray.length > 0
                    ? getContentByte(requestContentByteArray, httpServletRequest.getContentType())
                    : "";

            int responseStatus = httpServletResponse.getStatus();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String responseStatusPhrase;
            try {
                responseStatusPhrase = HttpStatus.valueOf(responseStatus).getReasonPhrase();
            } catch (Exception e) {
                responseStatusPhrase = "";
            }

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<String, String> responseHeaders = new HashMap<>();

            // Collection<String>으로 반환된다고 가정
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Collection<String> responseHeaderNames = httpServletResponse.getHeaderNames();
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull String headerName : responseHeaderNames) {
                String headerValue = httpServletResponse.getHeader(headerName);
                responseHeaders.put(headerName, headerValue);
            }

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] responseContentByteArray = httpServletResponse.getContentAsByteArray();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String responseBody = responseContentByteArray.length > 0
                    ? (httpServletResponse.getContentType().startsWith("text/html") ? "HTML Content" :
                    (httpServletRequest.getRequestURI().startsWith("/v3/api-docs") ||
                            httpServletRequest.getRequestURI().equals("/swagger-ui/swagger-initializer.js") ? "Skip" :
                            getContentByte(responseContentByteArray, httpServletResponse.getContentType())))
                    : "";

            // 로깅 처리
            // !!!로그 시작 문자 설정!!!
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String loggingStart = ">>ApiFilterLog>>";
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String logMessage = String.format(
                    "%s\nrequestTime : %s\nendPoint : %s\nclient Ip : %s\nrequest Headers : %s\nrequest Body : %s\n->\nresponse Status : %d %s\nprocessing duration(ms) : %d\nresponse Headers : %s\nresponse Body : %s\n",
                    loggingStart,
                    requestTime,
                    endpoint,
                    clientAddressIp,
                    requestHeaders,
                    requestBody,
                    responseStatus,
                    responseStatusPhrase,
                    Duration.between(requestTime, LocalDateTime.now()).toMillis(),
                    responseHeaders,
                    responseBody
            );

            if (isError) {
                classLogger.error(logMessage);
            } else {
                classLogger.info(logMessage);
            }

            // response 복사
            if (httpServletRequest.isAsyncStarted()) { // DeferredResult 처리
                httpServletRequest.getAsyncContext().addListener(new AsyncListener() {
                    @Override
                    public void onComplete(AsyncEvent event) throws IOException {
                        httpServletResponse.copyBodyToResponse();
                    }

                    @Override
                    public void onTimeout(AsyncEvent event) {
                    }

                    @Override
                    public void onError(AsyncEvent event) {
                    }

                    @Override
                    public void onStartAsync(AsyncEvent event) {
                    }
                });
            } else {
                httpServletResponse.copyBodyToResponse();
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>
    private @Valid @NotNull @org.jetbrains.annotations.NotNull String getContentByte(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] content,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String contentType
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        MediaType mediaType = MediaType.valueOf(contentType);
        boolean visible = visibleTypeList.stream().anyMatch(visibleType -> visibleType.includes(mediaType));

        if (visible) {
            try {
                return new String(content, StandardCharsets.UTF_8);
            } catch (Exception e) {
                return content.length + " bytes content";
            }
        } else {
            return content.length + " bytes content";
        }
    }

    // ---------------------------------------------------------------------------------------------
    // <중첩 클래스 공간>
}
