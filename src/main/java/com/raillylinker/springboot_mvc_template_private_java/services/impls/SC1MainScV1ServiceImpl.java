package com.raillylinker.springboot_mvc_template_private_java.services.impls;

import com.raillylinker.springboot_mvc_template_private_java.data_sources.redis_map_components.redis1_main.Redis1_Map_RuntimeConfigIpList;
import com.raillylinker.springboot_mvc_template_private_java.services.SC1MainScV1Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


/*
    (세션 멤버 정보 가져오기)
    val authentication = SecurityContextHolder.getContext().authentication
    // 현 세션 멤버 이름 (비로그인 : "anonymousUser")
    val username: String = authentication.name
    // 현 세션 권한 리스트 (비로그인 : [ROLE_ANONYMOUS], 권한없음 : [])
    val roles: List<String> = authentication.authorities.map(GrantedAuthority::getAuthority)
    println("username : $username")
    println("roles : $roles")

    (세션 만료시간 설정)
    session.maxInactiveInterval = 60
    위와 같이 세션 객체에 만료시간(초) 를 설정하면 됩니다.
*/
@Service
public class SC1MainScV1ServiceImpl implements SC1MainScV1Service {
    public SC1MainScV1ServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile,
            @Value("${springdoc.swagger-ui.enabled}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean swaggerEnabled
    ) {
        this.activeProfile = activeProfile;
        this.swaggerEnabled = swaggerEnabled;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Boolean swaggerEnabled;

    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView api1HomePage(
            @org.jetbrains.annotations.NotNull
            HttpServletRequest httpServletRequest,
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            HttpSession session
    ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("for_sc1_n1_home_page/home_page");

        mv.addObject(
                "viewModel",
                new Api1ViewModel(
                        activeProfile,
                        swaggerEnabled
                )
        );

        return mv;
    }

    public record Api1ViewModel(String env, boolean showApiDocumentBtn) {
    }
}