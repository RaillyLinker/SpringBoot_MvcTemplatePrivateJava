package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

// [Spring Email 유틸]
public interface EmailSender {
    // 이메일 전송 메서드
    void sendMessageMail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName, // 이메일에 표시될 발송자 이름 (발송 이메일 주소는 application.yml 에 저장)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String[] receiverEmailAddressArray, // 수신자 이메일 배열
            String[] carbonCopyEmailAddressArray, // 참조자 이메일 배열
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String subject, // 이메일 제목
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message, // 이메일 내용
            List<File> sendFileList, // 첨부파일 리스트
            List<MultipartFile> sendMultipartFileList // 첨부파일 리스트 (멀티파트)
    ) throws MessagingException, UnsupportedEncodingException;

    // (ThymeLeaf 로 랜더링 한 HTML 이메일 발송)
    void sendThymeLeafHtmlMail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName, // 이메일에 표시될 발송자 이름 (발송 이메일 주소는 application.yml 에 저장)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String[] receiverEmailAddressArray, // 수신자 이메일 배열
            String[] carbonCopyEmailAddressArray, // 참조자 이메일 배열
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String subject, // 이메일 제목
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String thymeLeafTemplateName, // thymeLeaf Html 이름 (ex : resources/templates/test.html -> "test")
            Map<String, Object> thymeLeafDataVariables, // thymeLeaf 템플릿에 제공할 정보 맵
            Map<String, File> thymeLeafCidFileMap, // cid 파일 리스트 (파일 경로 사용)
            Map<String, ClassPathResource> thymeLeafCidFileClassPathResourceMap, // cid 파일 리스트 (클래스패스 리소스 사용)
            List<File> sendFileList, // 첨부파일 리스트
            List<MultipartFile> sendMultipartFileList // 첨부파일 리스트 (멀티파트)
    ) throws MessagingException, UnsupportedEncodingException;
}
