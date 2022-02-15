package com.apitest.demo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    // request 로 들어오는 jwt 의 유효성을 검증 JwtProvider.validationToken()을 필터로서 FilterChain에 추가한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // request에서 token을 취한다.
        // 헤더에서 JWT를 받아온다.
        String accessToken = jwtProvider.resolveAccessToken((HttpServletRequest) request);

        // 검증 (log를 찍어서 확인)
        log.info("[verifying token]");
        log.info(accessToken);
        log.info(( (HttpServletRequest) request).getRequestURL().toString());

        // 유효한 Token인지 확인한다.
        if(accessToken != null) {
            // accessToken이 유효한 상황
            if(jwtProvider.validdateToken(accessToken)) {
                //true이면 아래의 메소드 호출
                this.setAuthentication(accessToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    // SecurityContext에 Authentication 객체를 저장한다.
    public void setAuthentication(String token) { //토큰으로부터 유저정보를 받아온다.

        // 토큰이 유효하면 토큰으로부터 유저정보를 받아온다.
        Authentication authentication = jwtProvider.getAuthentication(token);

        // SecurityContext에 Authentication 객체를 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
