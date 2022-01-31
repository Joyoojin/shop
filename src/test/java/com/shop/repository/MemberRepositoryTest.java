package com.shop.repository;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*테스트 코드 -- 미완성 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;
    @Autowired
    private MemberService memberService;

/*
    @Test
    @DisplayName("전체 회원수 출력 테스트")
    void countMember() {
        Member member = new Member();
        memberService.saveMember(member);
        Member count = memberRepository.countMember();
        System.out.println(count.toString());
    }

 */
}