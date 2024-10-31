package com.raillylinker.springboot_mvc_template_private_java.util_components;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

// [JWT 토큰 유틸]
public interface JwtTokenUtil {
    // <공개 메소드 공간>
    // (액세스 토큰 발행)
    // memberRoleList : 멤버 권한 리스트 (ex : ["ROLE_ADMIN", "ROLE_DEVELOPER"])
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String generateAccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long accessTokenExpirationTimeSec,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String issuer,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> roleList
    );

    // (리프레시 토큰 발행)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String generateRefreshToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long refreshTokenExpirationTimeSec,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String issuer,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString
    );

    // (JWT Secret 확인)
    // : 토큰 유효성 검증. 유효시 true, 위변조시 false
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean validateSignature(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString
    );

    // (JWT 정보 반환)
    // Member Uid
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Long getMemberUid(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey
    );

    // (Token 용도 (access or refresh) 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String getTokenUsage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey
    );

    // (멤버 권한 리스트 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<String> getRoleList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey
    );

    // (발행자 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String getIssuer(@Valid @NotNull @org.jetbrains.annotations.NotNull String token);

    // (토큰 남은 유효 시간(초) 반환 (만료된 토큰이라면 0))
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Long getRemainSeconds(@Valid @NotNull @org.jetbrains.annotations.NotNull String token);

    // (토큰 만료 일시 반환)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    LocalDateTime getExpirationDateTime(@Valid @NotNull @org.jetbrains.annotations.NotNull String token);

    // (토큰 타입)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String getTokenType(@Valid @NotNull @org.jetbrains.annotations.NotNull String token);
}
