package com.shop.repository;

import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

//( 회원가입 )

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {


    Member findByMemIDOrEmail(String memID, String email); // 회원 가입시 아이디와 email 로 중복회원 검사 //

    Member findByMemID(String memID);

    Member findByEmail(String email);

    List<Member> findByMemIDContaining(String keyword);

    void deleteByMemID(String memID);

}

