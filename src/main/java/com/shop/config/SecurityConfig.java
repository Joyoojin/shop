package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//(시큐리티 -회원가입 비밀번호)  (로그인,로그아웃)

/*
SecuriyConfig.java 의 역할 >
스프링 시큐리티를 추가하면 기본적으로 모든 요청에 인증을 필요로 하게 실행되기 때문에,
SecuriyConfig.java 의 configure 메소드 설정 추가 작성( 요청에 인증을 요구 하지 않도록 설정 가능해짐.)
*/

@Configuration
@EnableWebSecurity
// WebSecurityConfigurerAdapter을 상속 받는 클래스에 @EnableWebSecurity 어노테이션 선언 -> SpringSecurityFilterChain 자동 포함됨.
public class SecurityConfig extends WebSecurityConfigurerAdapter {   //WebSecurityConfigurerAdapter 를 상속받아서 메소드 오버라이딩을 통한 보안 설정 커스터마이징 가능.

    @Autowired
    MemberService memberService;


    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 요청에 대한 보안 설정 : 페이지 권한, 로그인 페이지, 로그아웃 메소드 등 설정.
        //로그인
        http.formLogin()
                .loginPage("/members/login")    //로그인 페이지 URL 설정
                .defaultSuccessUrl("/")         //로그인 성공시 이동할 URL
                .usernameParameter("email")     //로그인 시 사용할 파라미터 이름으로 email 지정
                .failureUrl("/members/login/error")//로그인 실패 시 이동할 URL 설정
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 URL 설정
                .logoutSuccessUrl("/")              //로그아웃 성공시 이동할 URL
        ;
        //페이지 접근 권한
        http.authorizeRequests()            //시큐리티 처리에 httpServletRequest 이용

                .mvcMatchers("/", "/category/items/**", "/members/**", "/item/**", "/images/**", "/event/**", "/test/**", "/test2/**", "/map/**", "/boardList/**").permitAll() // permitAll 을 통해 모든 사용자가 로그인 없이 (메인페이지,회원관련url, 상품상세 페이지, 상품 이미지 불러오는 경로)등 경로로 접근 가능 설정.
                /**유진 추가.  */
                .mvcMatchers("/admin/**", "/members/adminMembers/**", "/boardList/admin/notice/new", "/boardList/notice/edit/**").hasRole("ADMIN")      //   /admin 으로 시작하는 경로는 ADMIN Role 인 경우에만 접근 가능 설정
                .anyRequest().authenticated()      // 그외 나머지 경로는 모두 인증 요구하도록 설정.
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())  // 인증되지 않은 사용자가 리소스 접근시 수행하는 핸들러 등록
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // 회원가입 비밀번호 : 해킹 대비한 비밀번호 암호화 저장하기. BCryptPasswordEncoder을 빈으로 등록하여 사용하기.


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { ///*스프링 시큐리티에서 인증은 AuthenticationManagerBuilder 가 생성한 AuthenticationManager를 통해 이루어짐
        auth.userDetailsService(memberService)      // userDetailsService를 구현하고 있는 객체로 memberService 지정해주며,
                .passwordEncoder(passwordEncoder()); // 비밀번호 암호화를 위해  passwordEncoder 지정

    }

    /**
     * 유진 수정  "/images/**" 추가
     */

    //페이지 권한
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/images/**"); // static 디렉터리의 하위 파일은 인증을 무시하고 누구나 볼수 있도록 설정

    }

}