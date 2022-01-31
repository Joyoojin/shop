package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc   // MockMVC 테스트를 위한 어노테이션
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;  // MockMvc 클래스를 이용한 테스트.MockMvc 객체(실제 객체와 비슷하지만 테스트에 필요한 기능만 가진 가짜 객체)를 이용하면 웹 브라우저에서 요청을 하는 것 처럼 테스트 할 수 있음.

    public Member createMember(String email, String password) {  //로그인 테스트 진행을 위해서 로그인 전 가상 회원 등록하는 메소드.
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setMemID("test");
        memberFormDto.setEmail(email);
        memberFormDto.setName("김유신");
        memberFormDto.setPostcode("16100");
        memberFormDto.setRoadAddress("서울시 금천구 ");
        memberFormDto.setJibunAddress("16번지 ");
        memberFormDto.setDetailAddress("가산동 101-1");
        memberFormDto.setExtraAddress("(가산디지털단지역)");
        memberFormDto.setPassword(password);
        memberFormDto.setMemPhone("01012345678"); /**  추가 */
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@naver.com";
        String password = "1234";                         //회원가입시 입력할 패스워드
        this.createMember(email, password);             //회원가입 메소드가 실행 후
        mockMvc.perform(formLogin().userParameter("email")  //userParameter 이용하여 이메일을 아이디로 세팅하고
                        .loginProcessingUrl("/members/login")            //로그인 url 에  요청.
                        .user(email).password(password))                //일치하는 패스워드로 로그인 시 입력
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); //authenticated // 로그인 성공하여 인증 되었다면 테스트 코드  통과
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@naver.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email).password("12345"))             //일치하지 않는 비밀번호 입력
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());    //unauthenticated //회원가입시 입력한 번호가 아닌 다른 번호로 로그인 시도하였기 때문에, 인증되지 않은 결과 값이 출력되어 테스트 통과
    }

}