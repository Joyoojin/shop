package com.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*<!-- 로그인 로그아웃 role 에 따른 페이지 권한 설정.
 인증되지 않은 사용자가 리소스 요청할 경우, Unauthorized 권한없다는 에러가 발생하도록
 AuthenticationEntryPoint 인터페이스 구현-->*/

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {  // AuthenticationEntryPoint 인터페이스 구현

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 인증되지 않은 사용자가 리소스 요청할 경우, Unauthorized 에러 발생하도록
    }

}