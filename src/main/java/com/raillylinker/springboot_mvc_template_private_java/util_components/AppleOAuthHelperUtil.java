package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// [Apple OAuth2 검증 관련 유틸]
public interface AppleOAuthHelperUtil {
    // 애플 Id Token 검증 함수 - 검증이 완료되었다면 프로필 정보가 반환되고, 검증되지 않는다면 null 반환
    AppleProfileData getAppleMemberData(@Valid @NotNull @org.jetbrains.annotations.NotNull String idToken) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException;

    record AppleProfileData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String snsId,
            String email
    ) {
    }
}
