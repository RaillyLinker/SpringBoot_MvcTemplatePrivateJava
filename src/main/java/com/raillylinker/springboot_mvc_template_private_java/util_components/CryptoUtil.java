package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

// [암호화, 복호화 관련 유틸]
public interface CryptoUtil {
    // [암호화 / 복호화]
    // (AES256 암호화)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String encryptAES256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String text, // 암호화하려는 평문
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String alg, // 암호화 알고리즘 (ex : "AES/CBC/PKCS5Padding")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector, // 초기화 벡터 16byte = 16char
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey // 암호화 키 32byte = 32char
    );

    // (AES256 복호화)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String decryptAES256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String cipherText, // 복호화하려는 암호문
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String alg, // 암호화 알고리즘 (ex : "AES/CBC/PKCS5Padding")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector, // 초기화 벡터 16byte = 16char
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey // 암호화 키 32byte = 32char
    );


    ///////////////////////////////////////////////////////////////////////////////////////////
    // [인코딩 / 디코딩]
    // (Base64 인코딩)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String base64Encode(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String str
    );

    // (Base64 디코딩)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String base64Decode(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String str
    );


    ///////////////////////////////////////////////////////////////////////////////////////////
    // [해싱]
    // (SHA256 해싱)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String hashSHA256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String str
    );

    // (HmacSHA256)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String hmacSha256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String data,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String secret
    );
}
