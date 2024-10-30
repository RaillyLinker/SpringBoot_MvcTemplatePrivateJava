package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.raillylinker.springboot_mvc_template_private_java.util_components.CustomUtil;
import com.raillylinker.springboot_mvc_template_private_java.util_components.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// [Spring Email 유틸]
@Component
public class EmailSenderImpl implements EmailSender {
    public EmailSenderImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CustomUtil customUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JavaMailSender javaMailSender,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            @Value("${custom-config.smtp.sender-name}") String mailSenderName
    ) {
        this.customUtil = customUtil;
        this.javaMailSender = javaMailSender;
        this.mailSenderName = mailSenderName;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final CustomUtil customUtil;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final JavaMailSender javaMailSender;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String mailSenderName;


    @Override
    public void sendMessageMail(
            // 이메일에 표시될 발송자 이름 (발송 이메일 주소는 application.yml 에 저장)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName,
            // 수신자 이메일 배열
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String[] receiverEmailAddressArray,
            // 참조자 이메일 배열
            String[] carbonCopyEmailAddressArray,
            // 이메일 제목
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String subject,
            // 이메일 내용
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message,
            // 첨부파일 리스트
            List<File> sendFileList,
            // 첨부파일 리스트 (멀티파트)
            List<MultipartFile> sendMultipartFileList
    ) throws MessagingException, UnsupportedEncodingException {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Boolean isMultipart = (sendFileList != null && !sendFileList.isEmpty()) ||
                (sendMultipartFileList != null && !sendMultipartFileList.isEmpty()); // multipart 전송 여부
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");

        mimeMessageHelper.setFrom(new InternetAddress(mailSenderName, senderName)); // 발송자명 적용
        mimeMessageHelper.setTo(receiverEmailAddressArray); // 수신자 이메일 적용
        if (carbonCopyEmailAddressArray != null) {
            mimeMessageHelper.setCc(carbonCopyEmailAddressArray); // 참조자 이메일 적용
        }
        mimeMessageHelper.setSubject(subject); // 메일 제목 적용
        mimeMessageHelper.setText(message, false); // 메일 본문 내용 적용

        // 첨부파일 적용
        if (sendFileList != null) {
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull File sendFile : sendFileList) {
                mimeMessageHelper.addAttachment(sendFile.getName(), sendFile);
            }
        }

        if (sendMultipartFileList != null) {
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull MultipartFile sendFile : sendMultipartFileList) {
                mimeMessageHelper.addAttachment(Objects.requireNonNull(sendFile.getOriginalFilename()), sendFile);
            }
        }

        javaMailSender.send(mimeMessage);
    }

    // (ThymeLeaf 로 랜더링 한 HTML 이메일 발송)
    @Override
    public void sendThymeLeafHtmlMail(
            // 이메일에 표시될 발송자 이름 (발송 이메일 주소는 application.yml 에 저장)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName,
            // 수신자 이메일 배열
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String[] receiverEmailAddressArray,
            // 참조자 이메일 배열
            String[] carbonCopyEmailAddressArray,
            // 이메일 제목
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String subject,
            // thymeLeaf Html 이름 (ex : resources/templates/test.html -> "test")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String thymeLeafTemplateName,
            // thymeLeaf 템플릿에 제공할 정보 맵
            Map<String, Object> thymeLeafDataVariables,
            // thymeLeaf 내에 사용할 cid 파일 리스트
            /*
                 Map 타입 변수는 (변수명, 파일 경로)의 순서이며,
                 thymeLeafCidFileMap 은 ("image1", File("d://document/images/image-1.jpeg")) 이렇게,
                 thymeLeafCidFileClassPathResourceMap 은 ("image2", ClassPathResource("static/images/image-2.jpeg")) 이렇게 입력하고,
                 img 테그의 src 에는 'cid:image1' 혹은 'cid:image2' 이렇게 표시
             */
            Map<String, File> thymeLeafCidFileMap,
            Map<String, ClassPathResource> thymeLeafCidFileClassPathResourceMap,
            // 첨부파일 리스트
            List<File> sendFileList,
            // 첨부파일 리스트 (멀티파트)
            List<MultipartFile> sendMultipartFileList
    ) throws MessagingException, UnsupportedEncodingException {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom(new InternetAddress(mailSenderName, senderName)); // 발송자명 적용
        mimeMessageHelper.setTo(receiverEmailAddressArray); // 수신자 이메일 설정

        if (carbonCopyEmailAddressArray != null) {
            mimeMessageHelper.setCc(carbonCopyEmailAddressArray); // 참조자 이메일 설정
        }

        mimeMessageHelper.setSubject(subject);

        // 타임리프 HTML 랜더링
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String htmlString = customUtil.parseHtmlFileToHtmlString(thymeLeafTemplateName, thymeLeafDataVariables);
        mimeMessageHelper.setText(htmlString, true);

        // cid 파일 적용
        if (thymeLeafCidFileMap != null) {
            for (Map.Entry<String, File> entry : thymeLeafCidFileMap.entrySet()) {
                mimeMessageHelper.addInline(entry.getKey(), entry.getValue());
            }
        }

        if (thymeLeafCidFileClassPathResourceMap != null) {
            for (Map.Entry<String, ClassPathResource> entry : thymeLeafCidFileClassPathResourceMap.entrySet()) {
                mimeMessageHelper.addInline(entry.getKey(), entry.getValue());
            }
        }

        // 첨부파일 적용
        if (sendFileList != null) {
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull File sendFile : sendFileList) {
                mimeMessageHelper.addAttachment(sendFile.getName(), sendFile);
            }
        }

        if (sendMultipartFileList != null) {
            for (MultipartFile sendFile : sendMultipartFileList) {
                mimeMessageHelper.addAttachment(Objects.requireNonNull(sendFile.getOriginalFilename()), sendFile);
            }
        }

        javaMailSender.send(mimeMessage);
    }
}
