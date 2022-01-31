package com.shop.repository;

import com.shop.entity.Member;

import java.util.List;


public interface MemberRepositoryCustom {


    // 관리자 회원조회
    List<Member> findAll();

    void deleteByMemID(String memID);
}