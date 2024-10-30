package com.raillylinker.springboot_mvc_template_private_java.util_components.impls;

import com.raillylinker.springboot_mvc_template_private_java.util_components.CryptoUtil;
import com.raillylinker.springboot_mvc_template_private_java.util_components.CustomUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

// [암호화, 복호화 관련 유틸]
@Component
public class CryptoUtilImpl implements CryptoUtil {
    public CryptoUtilImpl(@Valid @NotNull @org.jetbrains.annotations.NotNull CustomUtil customUtil) {
        this.customUtil = customUtil;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final CustomUtil customUtil;

    // [암호화 / 복호화]
    // (AES256 암호화)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String encryptAES256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String text, // 암호화하려는 평문
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String alg, // 암호화 알고리즘 (ex : "AES/CBC/PKCS5Padding")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector, // 초기화 벡터 16byte = 16char
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey // 암호화 키 32byte = 32char
    ) {
        if (encryptionKey.length() != 32 || initializationVector.length() != 16) {
            throw new RuntimeException("encryptionKey length must be 32 and initializationVector length must be 16");
        }

        try {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Cipher cipher = Cipher.getInstance(alg);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            IvParameterSpec ivParamSpec = new IvParameterSpec(initializationVector.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // (AES256 복호화)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String decryptAES256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String cipherText, // 복호화하려는 암호문
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String alg, // 암호화 알고리즘 (ex : "AES/CBC/PKCS5Padding")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector, // 초기화 벡터 16byte = 16char
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey // 암호화 키 32byte = 32char
    ) {
        if (encryptionKey.length() != 32 || initializationVector.length() != 16) {
            throw new RuntimeException("encryptionKey length must be 32 and initializationVector length must be 16");
        }

        try {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Cipher cipher = Cipher.getInstance(alg);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            IvParameterSpec ivParamSpec = new IvParameterSpec(initializationVector.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // [인코딩 / 디코딩]
    // (Base64 인코딩)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String base64Encode(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String str
    ) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    // (Base64 디코딩)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String base64Decode(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String str
    ) {
        return new String(Base64.getDecoder().decode(str));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // [해싱]
    // (SHA256 해싱)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String hashSHA256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String str
    ) {
        try {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return customUtil.bytesToHex(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // (HmacSHA256)
    @Override
    public @Valid @NotNull @org.jetbrains.annotations.NotNull String hmacSha256(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String data,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String secret
    ) {
        try {
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            sha256Hmac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
