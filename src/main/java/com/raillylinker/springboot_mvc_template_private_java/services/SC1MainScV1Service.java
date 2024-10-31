package com.raillylinker.springboot_mvc_template_private_java.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.servlet.ModelAndView;

public interface SC1MainScV1Service {
    // (홈페이지 반환)
    ModelAndView api1HomePage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletRequest httpServletRequest,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpSession session
    );
}