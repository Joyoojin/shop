package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//회원가입 기능 테스트

/**
 * 수정 사항 : createMember 테스트 입력값 (초록색) 수정 작성.
 */

@SpringBootTest
@Transactional  // 테스트 실행 후 롤백처리되므로 반복 테스트 가능
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 수정 사항 : createMember 테스트 입력값 수정 ,추가 작성.
     */
    public Member createMember() {  // 회원정보 입력한 Member 엔티티 만드는 메소드
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setMemID("test");          /**  추가 */
        memberFormDto.setEmail("test@naver.com");
        memberFormDto.setName("김유신");
        memberFormDto.setPostcode("16100");
        memberFormDto.setRoadAddress("서울시 금천구 ");
        memberFormDto.setJibunAddress("16번지 ");
        memberFormDto.setDetailAddress("가산동 101-1");
        memberFormDto.setExtraAddress("(가산디지털단지역)");
        memberFormDto.setPassword("1234");
        memberFormDto.setMemPhone("01012345678"); /** 추가 */
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {  //Junit의 Assertions 클래스의 assertEquals 메소드 이용하여 저장하려고 요청했던 값과 실제 저장된 데이터 비교.
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        assertEquals(member.getMemID(), savedMember.getMemID());  /** 추가 */
        assertEquals(member.getEmail(), savedMember.getEmail());  //첫번째 파라미터에는 기대값, 두번째 파라미터에는 실제로 저장된 값.
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getMemPhone(), savedMember.getMemPhone()); /** 추가 */
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);
        Throwable e = assertThrows(IllegalStateException.class, () -> {  // junit의 Assertions 클래스의 assertThrows 메소드 이용한 예외처리 테스트.. 발생할 예외 타입을 첫번째 파라미터로 넣어줌
            memberService.saveMember(member2);
        });   // member2라는 중복 회원 가입했을 때
        assertEquals("이미 가입된 회원입니다.", e.getMessage());  //발생한 예외메세지가 예상결과와 맞는지 확인
    }


}